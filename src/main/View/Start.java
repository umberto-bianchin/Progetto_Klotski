package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

class Start extends JPanel{

    private final JButton[] buttons = new JButton[4];


    public Start(){

        setLayout(new GridLayout(2,2));
        setOpaque(false);
        setBounds(32, 100, 420, 440);

        for(int i=0; i<4; i++) {
            JLabel label = new JLabel();

            label.setOpaque(false);
            buttons[i] = new JButton(new ImageIcon("./src/images/miniatura"+i+".png"));
            buttons[i].setName(String.valueOf(i));
            buttons[i].setBounds(60,5, 155, 180);
            buttons[i].setBorder(null);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBackground(null);
            label.add(buttons[i]);

            add(label);
        }

    }

    public void addConfigurationListener(MouseAdapter listener){
        for(JButton button : buttons)
            button.addMouseListener(listener);
    }

}
