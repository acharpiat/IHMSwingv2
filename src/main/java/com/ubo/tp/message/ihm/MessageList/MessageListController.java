package main.java.com.ubo.tp.message.ihm.MessageList;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.login.LoginView;
import main.java.com.ubo.tp.message.ihm.session.Session;

import javax.swing.*;

public class MessageListController implements IDatabaseObserver {
    protected MessageListView messageListView;

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
    public MessageListController(IDatabase database, EntityManager entityManager, Session session) {
        this.mDatabase = database;
        this.mDatabase.addObserver(this);
        this.mEntityManager = entityManager;
        this.session = session;
        messageListView = new MessageListView();

        initView();
    }

    public void initView() {
        messageListView.showMessagesList(mDatabase.getMessages());
    }

    public JPanel getView() {
        return messageListView;
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {
            messageListView.showMessagesList(mDatabase.getMessages());
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
