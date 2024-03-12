package main.java.com.ubo.tp.message.ihm.message;

import main.java.com.ubo.tp.message.datamodel.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageView extends JPanel{

    JButton buttonSendMessage;

    JButton buttonSearchMessage;

    JButton buttonShowMessageList;
    JTextArea message;

    JPanel messagePanel;

    JTextField messageSearch;

    public MessageView() {
        super();

        initView();
    }

    public void initView(){

        JLabel messagePrompt = new JLabel("Saisir un message");

        message = new JTextArea();
        message.setRows(4);
        message.setLineWrap(true);

        JLabel searchMessages = new JLabel("Rechercher un message");


        messageSearch = new JTextField();

        buttonSendMessage = new JButton("Envoyer");

        buttonSearchMessage = new JButton("Rechercher");

        buttonShowMessageList = new JButton("Afficher la liste des messages");

        messagePanel = new JPanel();
        messagePanel.setLayout(new GridBagLayout());
        JScrollPane scrollPaneMessages = new JScrollPane(messagePanel);


        JScrollPane scrollPane = new JScrollPane(message);
        setLayout(new GridBagLayout());

        add(messagePrompt, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        add(searchMessages, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(buttonSendMessage, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(messageSearch, new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(buttonSearchMessage, new GridBagConstraints(0, 7, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(buttonShowMessageList, new GridBagConstraints(0,8, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(scrollPaneMessages, new GridBagConstraints(0,9, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


    }

    public void addSendListener(Runnable runnable) {
        buttonSendMessage.addActionListener(e -> runnable.run());
    }

    public void addLastSentListener(Runnable runnable) {
        buttonSearchMessage.addActionListener(e -> runnable.run());
    }

    public void addMessageListListener(Runnable runnable) {
        buttonShowMessageList.addActionListener(e -> runnable.run());
    }




    public String getMessageValue() {
        return message.getText();
    }

    public String getSearchValue() {
        return messageSearch.getText();
    }

    public void afficherMessages(String list){
        JOptionPane.showMessageDialog(this, list);
    }

    public void showMessagesList(Set<Message> messages){
        for(int i = 0; i < messages.size(); i++){
            messagePanel.add(new MessageComponent(new ArrayList<>(messages).get(i).getText()), new GridBagConstraints(0,i, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        }
        messagePanel.revalidate();
    }
}
