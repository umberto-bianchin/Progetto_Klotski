package View;

import javax.swing.*;
import java.awt.*;

public class Buttons extends JPanel {

    public Buttons(){

        setBackground(Color.white);
        setLayout(new GridLayout(4,1));
        setSize(100,200);

        ImageIcon reset = new ImageIcon("./src/images/restart.png");
        ImageIcon save = new ImageIcon("./src/images/save.png");
        ImageIcon next = new ImageIcon("./src/images/next.png");
        ImageIcon undo = new ImageIcon("./src/images/undo.png");

        JButton[] buttons = new JButton[4];
        buttons[0] = new JButton(reset);
        buttons[1] = new JButton(save);
        buttons[2] = new JButton(next);
        buttons[3] = new JButton(undo);

        for(JButton button : buttons){
            button.setBorder(null);
            button.setBackground(Color.white);
            add(button);
        }

    }
}
