package main.java.com.ubo.tp.message.ihm.login;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {

	private JPasswordField password;
	private JLabel passwordPrompt;

	private JLabel tagPrompt;
	private JTextField tag;

	private JButton buttonLogin;

	public LoginView() {
		super();

		initView();
	}

	public void initView() {
		tagPrompt = new JLabel("Tag : ");
		tag = new JTextField();

		password = new JPasswordField();
		passwordPrompt = new JLabel("Password : ");

		buttonLogin = new JButton("Connect");

		setLayout(new GridBagLayout());

		add(tagPrompt, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(tag, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		add(passwordPrompt, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(password, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		add(buttonLogin, new GridBagConstraints(0, 3, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

	}

	public void addLoginListener(Runnable listener) {
		buttonLogin.addActionListener(e -> listener.run());
	}

	public String getTagValue() {
		return tag.getText();
	}

	public String getPasswordValue() {
		return String.valueOf(password.getPassword());
	}

	public void setTagBackground(Color color) {
		tag.setBackground(color);
	}
}
