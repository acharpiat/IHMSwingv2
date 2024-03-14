package main.java.com.ubo.tp.message.ihm.message;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.resources.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MessageComponent extends JPanel {

    JLabel m;
    public MessageComponent(Message message) {
        super();
        if(message.getSender().getAvatarPath() == null){
            message.getSender().setAvatarPath("D:\\M2\\IHM\\LUCAS\\MessageApp\\src\\main\\resources\\images\\img_2.png");
        }
        File file = new File(message.getSender().getAvatarPath());
        ImagePanel imagePanel = new ImagePanel(file, new Dimension(80, 80));
        m = new JLabel(message.getText());
        JLabel sender = new JLabel(message.getSender().getUserTag() + " dit :");
        JPanel customPanel = new JPanel();
        customPanel.setLayout(new GridBagLayout());
        customPanel.add(imagePanel, new GridBagConstraints(0, 0, 1, 2, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        customPanel.add(sender, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        customPanel.add(m, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        setLayout(new GridBagLayout());
        add(customPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        setBackground(Color.lightGray);
        add(m);

    }

    public void setMessage(String message){
        m.setText(message);
    }

    public String getMessage(){
        return m.getText();
    }
}
