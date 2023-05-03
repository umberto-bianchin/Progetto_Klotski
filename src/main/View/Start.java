package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class Start extends JPanel{

    private final JButton[] buttons = new JButton[4];


    public Start(){

        setLayout(new GridLayout(2,2));
        setOpaque(false);
        setBounds(32, 100, 420, 440);

        for(int i=0; i<4; i++) {
            ImageIcon myPicture = new ImageIcon("./src/images/miniatura"+i+".png");
            JLabel label = new JLabel();

            label.setOpaque(false);
            buttons[i] = new JButton(myPicture);
            buttons[i].setName(String.valueOf(i));
            buttons[i].setBounds(60,5, 150, 170);
            label.add(buttons[i]);

            add(label);
        }

    }

    public void addConfigurationListener(ActionListener listener){
        for(JButton button : buttons)
            button.addActionListener(listener);
    }

}
