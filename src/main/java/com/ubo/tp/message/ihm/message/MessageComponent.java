package main.java.com.ubo.tp.message.ihm.message;

import javax.swing.*;

public class MessageComponent extends JComponent {

    JLabel m;
    public MessageComponent(String message) {
        super();

        m = new JLabel(message);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(m);

    }

    public void setMessage(String message){
        m.setText(message);
    }

    public String getMessage(){
        return m.getText();
    }
}
