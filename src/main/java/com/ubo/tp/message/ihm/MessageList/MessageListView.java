package com.ubo.tp.message.ihm.MessageList;

import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.ihm.message.MessageComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class MessageListView extends JPanel {
    public MessageListView() {
        super();
        setLayout(new GridBagLayout());
    }


    public void showMessagesList(Set<Message> messages){
        this.removeAll();

        JPanel messagePanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(messagePanel);
        messagePanel.setLayout(new GridBagLayout());

        for(int i = 0; i < messages.size(); i++){
            messagePanel.add(new MessageComponent(new ArrayList<>(messages).get(i)), new GridBagConstraints(0,i, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.VERTICAL, new Insets(5, 5, 5, 5), 0, 0));
        }

        add(scrollPane, new GridBagConstraints(0, messages.size(), 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        revalidate();
        repaint();
    }
}
