package com.ubo.tp.message.ihm.login;

import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.session.Session;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LoginController {

	protected LoginView mLoginView;

	/**
	 * Base de donénes de l'application.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire de bdd et de fichier.
	 */
	protected EntityManager mEntityManager;

	protected Session session;

	/**
	 * Constructeur.
	 *
	 * @param database , Base de données de l'application.
	 * @param session
	 */
	public LoginController(IDatabase database, EntityManager entityManager, Session session) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
		this.session = session;
		mLoginView = new LoginView();

		initView();
	}

	protected void initView() {
		mLoginView.addLoginListener(() -> {
			String tagValue = mLoginView.getTagValue();
			String passwordValue = mLoginView.getPasswordValue();

			boolean tagIsValid = isValidInput(tagValue, mLoginView::setTagBackground);

			if (!tagIsValid) {
				return;
			}

			for (User u : mDatabase.getUsers()) {
				if (u.getUserTag().equalsIgnoreCase(tagValue) && u.getUserPassword().equals(passwordValue)) {
					JOptionPane.showMessageDialog(null, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
					session.connect(u);
					return;
				}
			}

			JOptionPane.showMessageDialog(mLoginView, "Incorrect information", "Error", JOptionPane.ERROR_MESSAGE);
		});
	}

	protected boolean isValidInput(String value, Consumer<Color> setBackground) {
		if (value.isEmpty()) {
			setBackground.accept(Color.RED);
			return false;
		} else {
			setBackground.accept(null);
			return true;
		}
	}

	public JPanel getView() {
		return mLoginView;
	}

}
