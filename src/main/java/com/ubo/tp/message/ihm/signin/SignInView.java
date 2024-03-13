package com.ubo.tp.message.ihm.signin;

import javax.swing.*;
import java.awt.*;

public class SignInView extends JPanel {

	private JLabel namePrompt;
	private JTextField name;

	private JPasswordField password;
	private JLabel passwordPrompt;

	private JLabel avatarPrompt;
	private JTextField avatar;

	private JLabel tagPrompt;
	private JTextField tag;

	private JButton buttonSignIn;

	private JButton backButton;

	public SignInView() {
		super();

		initView();
	}

	public void initView() {
		namePrompt = new JLabel("Name : ");
		name = new JTextField();

		password = new JPasswordField();
		passwordPrompt = new JLabel("Password : ");

		tagPrompt = new JLabel("Tag : ");
		tag = new JTextField();

		avatarPrompt = new JLabel("Avatar : ");
		avatar = new JTextField();

		buttonSignIn = new JButton("Sign in");

		setLayout(new GridBagLayout());

		add(namePrompt, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(name, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		add(passwordPrompt, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(password, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		add(tagPrompt, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(tag, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		add(avatarPrompt, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(avatar, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));


		add(buttonSignIn, new GridBagConstraints(0,4 , 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

	}

	public void addLoginListener(Runnable listener) {
		buttonSignIn.addActionListener(e -> listener.run());
	}

	public String getNameValue() {
		return name.getText();
	}

	public String getTagValue() {
		return tag.getText();
	}

	public String getPasswordValue() {
		return String.valueOf(password.getPassword());
	}

	public String getAvatarValue() {
		return avatar.getText();
	}

	public void setNameBackground(Color color) {
		name.setBackground(color);
	}

	public void setTagBackground(Color color) {
		tag.setBackground(color);
	}
}
