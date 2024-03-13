package com.ubo.tp.message.ihm.session;

import com.ubo.tp.message.ihm.ImagePanel;
import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.database.IDatabaseObserver;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.UUID;

public class SessionController implements ISessionObserver, IDatabaseObserver {

    protected SessionView mSessionView;


    /**
     * Base de donénes de l'application.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    protected EntityManager mEntityManager;

    protected Session session;


    public SessionController(IDatabase database, EntityManager entityManager, Session session) throws AWTException {
        this.mDatabase = database;
        this.mEntityManager = entityManager;

        this.session = session;
        this.session.addObserver(this);
        mDatabase.addObserver(this);

        mSessionView = new SessionView();

        initView();
    }

    protected void initView() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(new ImageIcon("D:\\M2\\IHM\\LUCAS\\MessageApp\\src\\main\\resources\\images\\logo_20.png").getImage(), "MessageApp");
        tray.add(trayIcon);
        mSessionView.addResearchListener(() -> {
            String users = "";
            for(User u : mDatabase.getUsers()){
                if(users.isEmpty()){
                    users = u.getUserTag() + " ";
                } else {
                    users = users + " / " + u.getUserTag();
                }
            }
            mSessionView.afficherUsers(users);
        });

        mSessionView.addOneResearchListener(() -> {
            String tagValue = mSessionView.getUserTag();
            boolean exists = false;
            File file = null;
            JLabel nom = null;
            JLabel userTag = null;
            JLabel uuid = null;
            for(User u : mDatabase.getUsers()){
                if(u.getUserTag().equals(tagValue)){
                    file = new File(u.getAvatarPath());
                    nom = new JLabel("Nom : " + u.getName());
                    userTag = new JLabel("UserTag : " + u.getUserTag());
                    uuid = new JLabel("UUID : " + u.getUuid());
                    exists = true;
                }
            }
            if(exists){
                ImagePanel imagePanel = new ImagePanel(file, new Dimension(80, 100));
                JPanel customPanel = new JPanel();
                customPanel.add(imagePanel);
                customPanel.add(nom);
                customPanel.add(userTag);
                customPanel.add(uuid);
                customPanel.setLayout(new GridBagLayout());
                customPanel.add(imagePanel, new GridBagConstraints(0, 0, 1, 4, 1, 1, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
                customPanel.add(nom, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
                customPanel.add(userTag, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
                customPanel.add(uuid, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
                JOptionPane.showMessageDialog(mSessionView,  customPanel, "Ses informations", JOptionPane.INFORMATION_MESSAGE, new ImageIcon());

                mSessionView.afficherUser();
            } else {
                JOptionPane.showMessageDialog(mSessionView,  "Utilisateur inconnu", "Nous sommes désolés...", JOptionPane.ERROR_MESSAGE);

                mSessionView.userNotFound();
            }
        });

        mSessionView.addProfileListener(() -> {
            File file = new File(session.getConnectedUser().getAvatarPath());
            ImagePanel imagePanel = new ImagePanel(file, new Dimension(80, 100));
            JLabel nom = new JLabel("Nom : " + session.getConnectedUser().getName());
            JLabel userTag = new JLabel("UserTag : " + session.getConnectedUser().getUserTag());
            JLabel uuid = new JLabel("UUID : " + session.getConnectedUser().getUuid());
            JPanel customPanel = new JPanel();
            customPanel.add(imagePanel);
            customPanel.add(nom);
            customPanel.add(userTag);
            customPanel.add(uuid);

            customPanel.setLayout(new GridBagLayout());
            customPanel.add(imagePanel, new GridBagConstraints(0, 0, 1, 4, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
            customPanel.add(nom, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
            customPanel.add(userTag, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
            customPanel.add(uuid, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
            JOptionPane.showMessageDialog(mSessionView,  customPanel, "Vos informations", JOptionPane.INFORMATION_MESSAGE, new ImageIcon());
        });
        mSessionView.addFollowListener(() -> {
            String followTagValue = mSessionView.getFollowUserTag();
            boolean exists = false;
            boolean followed = false;
            for(User u : mDatabase.getUsers()){
                if(u.getUserTag().equals(followTagValue)){
                    exists = true;
                }
                if(session.getConnectedUser().getFollows().contains(u.getUserTag())){
                    followed = true;
                }
            }
            if(exists && !followed){
                session.getConnectedUser().addFollowing(followTagValue);
                mEntityManager.writeUserFile(session.getConnectedUser());
                System.out.println("Abonné");
            } else if(followed){
                JOptionPane.showMessageDialog(mSessionView, "Déjà abonné", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mSessionView, "Utilisateur inconnu", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        mSessionView.addUnfollowListener(() -> {
            String followTagValue = mSessionView.getFollowUserTag();
            boolean exists = false;
            boolean followed = false;
            for(User u : mDatabase.getUsers()){
                if(u.getUserTag().equals(followTagValue)){
                    exists = true;
                }
                if(session.getConnectedUser().getFollows().contains(u.getUserTag())){
                    followed = true;
                }
            }
            if(followed){
                session.getConnectedUser().removeFollowing(followTagValue);
                mEntityManager.writeUserFile(session.getConnectedUser());
                System.out.println("Désabonné");
            } else if(!exists){
                JOptionPane.showMessageDialog(mSessionView, "Utilisateur inconnu", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mSessionView, "Non abonné", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getView() {
        return mSessionView;
    }

    @Override
    public void notifyLogin(User connectedUser) {

        mSessionView.updateText(connectedUser);
    }

    @Override
    public void notifyLogout() {
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {
        User envoyeur = addedMessage.getSender();
        User receveur = session.getConnectedUser();

        if (receveur == null || !receveur.isFollowing(envoyeur) || envoyeur.equals(receveur)) {
            return;
        } else {
            TrayIcon icon = SystemTray.getSystemTray().getTrayIcons()[0];
            System.out.println(icon);
            if(icon == null){
                return;
            } else {
                icon.setToolTip("Nouveau post de " + envoyeur.getUserTag() + " !");
                icon.displayMessage("Nouveau message", "Nouveau post de " + envoyeur.getUserTag() + " !", TrayIcon.MessageType.INFO);
            }
        }
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {

    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {

    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

    }

    @Override
    public void notifyUserModified(User modifiedUser) {

    }
}
