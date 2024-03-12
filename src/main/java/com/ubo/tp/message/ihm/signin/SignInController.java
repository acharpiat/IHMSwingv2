package main.java.com.ubo.tp.message.ihm.signin;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.session.Session;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Consumer;

public class SignInController {

	protected SignInView mSignInView;

	/**
	 * Base de données de l'application.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire de bdd et de fichier.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Constructeur.
	 *
	 * @param database , Base de données de l'application.
	 * @param session
	 */
	public SignInController(IDatabase database, EntityManager entityManager, Session session) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;

		mSignInView = new SignInView();

		initView();
	}

	protected void initView() {
		mSignInView.addLoginListener(() -> {
			String nameValue = mSignInView.getNameValue();
			String passwordValue = mSignInView.getPasswordValue();
			String tagValue = mSignInView.getTagValue();
			String avatarValue = mSignInView.getAvatarValue();

			boolean nameIsValid = isValidInput(nameValue, mSignInView::setNameBackground);
			boolean tagIsValid = isValidInput(tagValue, mSignInView::setTagBackground);

			if (!nameIsValid || !tagIsValid) {
				return;
			}

			for (User u : mDatabase.getUsers()) {
				if (u.getUserTag().equalsIgnoreCase(tagValue)) {
					mSignInView.setTagBackground(Color.RED);

					JOptionPane.showMessageDialog(null, "Le tag existe déjà !", "Erreur",
							JOptionPane.ERROR_MESSAGE);

					return;
				}
			}

			User user = new User(UUID.randomUUID(), tagValue, passwordValue, nameValue, new HashSet<>(), avatarValue);

			mEntityManager.writeUserFile(user);
		});
	}

	private boolean isValidInput(String value, Consumer<Color> setBackground) {
		if (value.isEmpty()) {
			setBackground.accept(Color.RED);
			return false;
		} else {
			setBackground.accept(Color.WHITE);
			return true;
		}
	}

	public JPanel getView() {
		return mSignInView;
	}

}
