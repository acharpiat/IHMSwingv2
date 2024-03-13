package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.ihm.session.Session;


import javax.swing.*;
import java.awt.*;

/**
 * Classe de la vue principale de l'application.
 */
public class MessageAppMainView {
	/**
	 * Fenetre du bouchon
	 */
	protected JFrame mFrame;

	/**
	 * Base de donénes de l'application.
	 */
	protected IDatabase mDatabase;

	protected Session session;

	/**
	 * Gestionnaire de bdd et de fichier.
	 */
	protected EntityManager mEntityManager;

	protected JButton signInButton;

	protected JButton loginButton;
	protected JMenuItem aboutMenuItem;

	protected JMenuItem disconnectMenuItem;

	protected JMenuItem listMessageMenuItem;
	protected JMenuItem exitMenuItem;

	protected JMenuItem messageMenuItem;
	protected JMenuItem lastMessageMenuItem;

	protected JMenuItem backMenuItem;

	/**
	 * Constructeur.
	 *
	 * @param database , Base de données de l'application.
	 */
	public MessageAppMainView(IDatabase database, EntityManager entityManager, Session session) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
		this.session = session;
	}

	/**
	 * Lance le choix du fichier.
	 */
	public String startFileChooser() {
		JFileChooser chooser = new JFileChooser("D:\\M2\\IHM\\trash");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = chooser.showOpenDialog(mFrame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}

		return null;
	}


	/**
	 * Lance l'afficahge de l'IHM.
	 */
	public void showGUI() {
		// Init auto de l'IHM au cas ou ;)
		if (mFrame == null) {
			this.initGUI();
		}

		// Affichage dans l'EDT
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Custom de l'affichage
				JFrame frame = MessageAppMainView.this.mFrame;
				Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation((screenSize.width - frame.getWidth()) / 6,
						(screenSize.height - frame.getHeight()) / 4);

				// Affichage
				MessageAppMainView.this.mFrame.setVisible(true);

				MessageAppMainView.this.mFrame.pack();
			}
		});
	}


	/**
	 * Initialise l'IHM.
	 */
	public void initGUI() {
		// Création de la fenetre
		mFrame = new JFrame("MessageApp");
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mFrame.setPreferredSize(new Dimension(800, 600));

		ImageIcon logoUbo50 = new ImageIcon("src\\main\\resources\\images\\logo_50.png");
		mFrame.setIconImage(logoUbo50.getImage());

		ImageIcon exitIcon20 = new ImageIcon("src\\main\\resources\\images\\exitIcon_20.png");
		ImageIcon logoUbo20 = new ImageIcon("src\\main\\resources\\images\\logo_20.png");



		// MENU

		JMenuBar bar = new JMenuBar();

		JMenu fileMenu = new JMenu("Fichier");

		exitMenuItem = new JMenuItem("Quitter", exitIcon20);
		exitMenuItem.setToolTipText("Fermer l'application");

		fileMenu.add(exitMenuItem);

		JMenu messageMenu = new JMenu("Message");
		messageMenuItem = new JMenuItem(" Envoyer un message");
		lastMessageMenuItem = new JMenuItem("Voir dernier message reçu");
		listMessageMenuItem = new JMenuItem("Voir la liste des messages");

		JMenu aboutMenu = new JMenu("?");
		aboutMenuItem = new JMenuItem("A propos");

		JMenu backMenu = new JMenu("Accueil");
		backMenuItem = new JMenuItem("Aller à l'accueil");




		JMenu disconnect = new JMenu("Déconnexion");
		disconnectMenuItem = new JMenuItem("Se déconnecter");


		messageMenu.add(listMessageMenuItem);
		messageMenu.add(lastMessageMenuItem);
		messageMenu.add(messageMenuItem);
		aboutMenu.add(aboutMenuItem);
		disconnect.add(disconnectMenuItem);
		backMenu.add(backMenuItem);


		bar.add(backMenu);
		bar.add(fileMenu);
		bar.add(messageMenu);
		bar.add(disconnect);
		bar.add(aboutMenu);
		mFrame.setJMenuBar(bar);

		signInButton = new JButton("S'inscrire");
		loginButton = new JButton("Se connecter");

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());

		panel.add(signInButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		panel.add(loginButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		mFrame.setContentPane(panel);
	}

	public void loginListener(Runnable runnable){
		loginButton.addActionListener(e -> {
			runnable.run();
		});
	}

	public void signInListener(Runnable runnable){
		signInButton.addActionListener(e -> {
			runnable.run();
		});
	}

	public void setPane(JPanel view){
		SwingUtilities.updateComponentTreeUI(view);

		mFrame.setContentPane(view);
		mFrame.validate();
	}

	public JPanel newPanelBack() {
		JPanel nouveauPanel = new JPanel();

		nouveauPanel.setLayout(new GridBagLayout());

		nouveauPanel.add(signInButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		nouveauPanel.add(loginButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		return nouveauPanel;
	}

	public void addAboutListener(Runnable runnable) {
		aboutMenuItem.addActionListener(e -> runnable.run());
	}

	public void addExitListener(Runnable runnable) {
		exitMenuItem.addActionListener(e -> runnable.run());
	}

	public void addDisconnectListener(Runnable runnable) {disconnectMenuItem.addActionListener(e -> runnable.run());
	}
	public void addBackListener(Runnable runnable) {backMenuItem.addActionListener(e -> runnable.run());}

	public void addMessageListener(Runnable runnable) {messageMenuItem.addActionListener(e -> runnable.run());
	}

	public void addLastMessageListener(Runnable runnable) {lastMessageMenuItem.addActionListener(e -> runnable.run());
	}

	public void addListMessageListener(Runnable runnable) {listMessageMenuItem.addActionListener(e -> runnable.run());
	}
}
