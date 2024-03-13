package main.java.com.ubo.tp.message.ihm.message;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.session.Session;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.ihm.session.SessionController;
import main.java.com.ubo.tp.message.ihm.signin.SignInController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Set;

public class MessageController {

    protected MessageView mMessageView;

    protected IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    protected EntityManager mEntityManager;

    protected Session session;

    public MessageController(IDatabase database, EntityManager entityManager, Session session) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.session = session;
        mMessageView = new MessageView();

        initView();
    }

    protected void initView() {
        mMessageView.addSendListener(() -> {
            String message = mMessageView.getMessageValue();


            if(message.length() >=200 || message == null){
                JOptionPane.showMessageDialog(null, "Message trop long ! 200 caractères max", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                Message m = new Message(session.getConnectedUser(), message);
                mEntityManager.writeMessageFile(m);

                System.out.println("Message envoyé !");
            }


        });
        mMessageView.addLastSentListener(() -> {
            String message = mMessageView.getSearchValue();
            Set<String> result = new HashSet<>();
            if (message.contains("#")) {
                message = message.substring(1);
                for (Message m : mDatabase.getMessagesWithTag(message)) {
                    result.add(m.getText());
                }
            } else if (message.contains("@")) {
                message = message.substring(1);
                for (Message m : mDatabase.getMessagesWithUserTag(message)) {
                    if (m.getText().contains(message)) {
                        result.add(m.getText());
                    }
                    for (User u : mDatabase.getUsers()) {
                        if (u.getUserTag().equals(message)) {
                            for (Message mess : mDatabase.getUserMessages(u)) {
                                result.add(mess.getText());
                            }
                        }
                    }
                }

            } else {
                for (Message m : mDatabase.getMessages()) {
                    if (m.getText().contains(message)) {
                        result.add(m.getText());
                    }
                }
                for (User u : mDatabase.getUsers()) {
                    if (u.getUserTag().equals(message)) {
                        for (Message mess : mDatabase.getUserMessages(u)) {
                            result.add(mess.getText());
                        }
                    }
                }
            }
            String finalString = "";
            for(String s : result){
                finalString += s + "\n";
            }
            mMessageView.afficherMessages(finalString);

        });

    }

    public JPanel getView() {
        return mMessageView;
    }
}
