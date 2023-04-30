package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class Start extends JPanel{

    private final JButton[] buttons = new JButton[4];


    public Start(){

        setLayout(new GridLayout(2,2));
        setOpaque(false);
        setBounds(10, 100, 530, 480);

        for(int i=0; i<4; i++) {
            ImageIcon myPicture = new ImageIcon("./src/images/miniatura"+i+".png");
            JLabel label = new JLabel();
            JLabel level_text = new JLabel("LEVEL: " + (i+1));
            level_text.setForeground(Color.white);

            level_text.setFont(new Font("Agency FB", Font.BOLD, 20));
            level_text.setBounds(90, 195, 150, 40);
            label.add(level_text);

            label.setOpaque(false);
            buttons[i] = new JButton(myPicture);
            buttons[i].setName(String.valueOf(i));
            buttons[i].setBounds(60,5, 150, 184);
            label.add(buttons[i]);

            add(label);
        }

    }

    public void addConfigurationListener(ActionListener listener){
        for(JButton button : buttons)
            button.addActionListener(listener);
    }

}
