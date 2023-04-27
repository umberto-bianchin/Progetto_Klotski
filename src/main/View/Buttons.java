package View;

import javax.swing.*;
import java.awt.*;

public class Buttons extends JPanel {

    public Buttons(){

        setBackground(Color.white);
        setLayout(null);

        ImageIcon restart = new ImageIcon("./src/images/restart.png");
        ImageIcon save = new ImageIcon("./src/images/save.png");
        ImageIcon next = new ImageIcon("./src/images/next.png");
        ImageIcon undo = new ImageIcon("./src/images/undo.png");

        JButton[] buttons = new JButton[4];
        buttons[0] = new JButton(restart);
        buttons[1] = new JButton(save);
        buttons[2] = new JButton(next);
        buttons[3] = new JButton(undo);

        buttons[0].setBounds(0, 0, 100, 100);
        buttons[1].setBounds(0, 120, 100, 100);
        buttons[2].setBounds(0, 240, 100, 100);
        buttons[3].setBounds(0, 360, 100, 100);


        for(JButton button : buttons){
            button.setBorder(null);
            button.setBackground(Color.white);
            add(button);
        }

    }
}
