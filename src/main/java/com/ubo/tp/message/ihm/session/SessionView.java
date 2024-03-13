package com.ubo.tp.message.ihm.session;

import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class SessionView extends JPanel {
    protected JLabel welcomeMessage;

    public JButton searchAllUsersButton;

    public JLabel label = new JLabel( " Users enregistrés : ");
    public JLabel usersList = new JLabel( "");

    public JTextField userTag;

    public JTextField userTagFollow;

    public JButton findUserButton;

    public JButton showProfileButton;

    public JButton followButton;

    public JButton unfollowButton;

    JLabel userFound;


    public SessionView() {
        super();

        initView();
    }

    public void initView(){
        welcomeMessage = new JLabel("Bienvenue sur votre espace ");
        userTag = new JTextField();
        showProfileButton = new JButton("Voir profil");
        findUserButton = new JButton("Chercher cet user");
        userFound = new JLabel(" ");
        searchAllUsersButton = new JButton("Afficher tous les utilisateurs");
        userTagFollow = new JTextField();
        followButton = new JButton("S'abonner");
        unfollowButton = new JButton("Se désabonner");
        setLayout(new GridBagLayout());
        add(welcomeMessage, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        add(searchAllUsersButton, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(userTag, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        add(findUserButton, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(userFound, new GridBagConstraints(3, 4, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(label, new GridBagConstraints(1, 1, 2, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(usersList, new GridBagConstraints(1, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(showProfileButton, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(userTagFollow, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(followButton, new GridBagConstraints(2, 6, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(unfollowButton, new GridBagConstraints(3, 6, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));


    }

    public void updateText(User connectedUser){
        welcomeMessage.setText("Bienvenue sur votre espace" + connectedUser.getName());

    }

    public void addResearchListener(Runnable runnable) {
        searchAllUsersButton.addActionListener(e -> runnable.run());
    }

    public void addOneResearchListener(Runnable runnable) {
        findUserButton.addActionListener(e -> runnable.run());
    }

    public void addProfileListener(Runnable runnable) {
        showProfileButton.addActionListener(e -> runnable.run());
    }

    public void addFollowListener(Runnable runnable) {
        followButton.addActionListener(e -> runnable.run());
    }

    public void addUnfollowListener(Runnable runnable) {
        unfollowButton.addActionListener(e -> runnable.run());
    }

    public void afficherUser(){
        System.out.println("User found");
        userFound.setText("Utilisateur existant");
    }

    public void userNotFound(){
        System.out.println("User not found");
        userFound.setText("Utilisateur non existant");
    }

    public String getUserTag(){
        return userTag.getText();
    }

    public String getFollowUserTag(){
        return userTagFollow.getText();
    }

    public void afficherUsers(String users){
        usersList.setText(users);
    }




}
