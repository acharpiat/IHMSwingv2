package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.DatabaseImpl;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.core.directory.IWatchableDirectory;
import main.java.com.ubo.tp.message.core.directory.WatchableDirectory;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.login.LoginController;
import main.java.com.ubo.tp.message.ihm.message.MessageComponent;
import main.java.com.ubo.tp.message.ihm.message.MessageController;
import main.java.com.ubo.tp.message.ihm.login.LoginView;
import main.java.com.ubo.tp.message.ihm.session.ISessionObserver;
import main.java.com.ubo.tp.message.ihm.session.Session;
import main.java.com.ubo.tp.message.ihm.session.SessionController;
import main.java.com.ubo.tp.message.ihm.signin.SignInController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageApp implements ISessionObserver {
	/**
	 * Base de données.
	 */
	protected IDatabase mDatabase;

	/**
	 * Impl base de données.
	 */
	protected IDatabaseObserver mDatabaseObserver;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	protected MessageAppMainView mMainView;

	/**
	 * Classe de surveillance de répertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange de l'application.
	 */
	protected String mExchangeDirectoryPath;

	protected Session session;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;

	protected SessionController sessionController;

	protected MessageController messageController;



	/**
	 * Constructeur.
	 *
	 * @param entityManager
	 * @param database
	 */
	public MessageApp(IDatabase database, EntityManager entityManager) throws AWTException {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
		this.session = new Session();
		this.mMainView = new MessageAppMainView(mDatabase, mEntityManager, session);

		this.mDatabaseObserver = new DatabaseImpl();
		this.sessionController = new SessionController(mDatabase, mEntityManager, session);
		this.messageController = new MessageController(mDatabase, mEntityManager, session);


		mDatabase.addObserver(mDatabaseObserver);
	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation du répertoire d'échange
		this.initDirectory();

		// Initialisation de l'IHM
		this.initGui();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {

		this.mMainView.initGUI();
		session.addObserver(this);
		mMainView.signInListener(() -> {
			this.mMainView.setPane(new SignInController(mDatabase, mEntityManager, session).getView());
		});

		mMainView.loginListener(() -> {
			this.mMainView.setPane(new LoginController(mDatabase, mEntityManager, session).getView());
		});

		JLabel label = new JLabel("<html><center>UBO M2-TIIL<br/>Département Informatique</center></html>");
		ImageIcon logoUbo50 = new ImageIcon("src\\main\\resources\\images\\logo_50.png");

		mMainView.addAboutListener(() -> {
			JOptionPane.showMessageDialog(null, label, "A propos", JOptionPane.INFORMATION_MESSAGE, logoUbo50);
		});

		mMainView.addExitListener(() -> {
			System.exit(0);
		});

		mMainView.addDisconnectListener(() -> {
			session.disconnect();
		});

		mMainView.addBackListener(() -> {
			mMainView.setPane(mMainView.newPanelBack());
		});

		mMainView.addMessageListener(() -> {
			mMainView.setPane(messageController.getView());
		});

		mMainView.addLastMessageListener(() -> {
			MessageComponent messageComponent = new MessageComponent(new ArrayList<>(mDatabase.getMessages()).get(mDatabase.getMessages().size()-1).getText());

			System.out.println("Last message : " + mDatabase.getMessages());
			JOptionPane.showMessageDialog(null, messageComponent.getMessage(), "A propos", JOptionPane.INFORMATION_MESSAGE, logoUbo50);
		});

	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
		String path = this.mMainView.startFileChooser();

		if (path != null && isValideExchangeDirectory(new File(path))) {
			this.initDirectory(path);
		} else {
			JOptionPane.showMessageDialog(null, "Le répertoire d'échange n'est pas valide.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
//			this.initDirectory();
		}
	}

	/**
	 * Indique si le fichier donné est valide pour servir de répertoire d'échange
	 *
	 * @param directory , Répertoire à tester.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du répertoire d'échange.
	 *
	 * @param directoryPath
	 */
	protected void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}

	public void show() {
		this.mMainView.showGUI();
	}

	@Override
	public void notifyLogin(User connectedUser) {

		System.out.println("User logged in");

		this.mMainView.setPane(sessionController.getView());
	}


	@Override
	public void notifyLogout() {
		mMainView.setPane(mMainView.newPanelBack());
	}
}
