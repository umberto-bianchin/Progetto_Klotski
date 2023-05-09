package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Buttons extends JPanel {

    private final JButton[] buttons = new JButton[5];

     Buttons(){

        setBackground(Color.white);
        setLayout(null);

        buttons[0] = new JButton(new ImageIcon("./src/images/restart.png"));
        buttons[1] = new JButton(new ImageIcon("./src/images/save.png"));
        buttons[2] = new JButton(new ImageIcon("./src/images/next.png"));
        buttons[3] = new JButton(new ImageIcon("./src/images/undo.png"));
        buttons[4] = new JButton(new ImageIcon("./src/images/home.png"));

         buttons[0].setBounds(0, 0, 100, 100);
         buttons[1].setBounds(0, 110, 100, 100);
         buttons[2].setBounds(0, 220, 100, 100);
         buttons[3].setBounds(0, 330, 100, 100);
         buttons[4].setBounds(0, 440, 100, 100);

        for(JButton button : buttons){
            button.setBorder(null);
            button.setBackground(Color.white);
            add(button);
        }
    }

    public void addButtonListener(ActionListener restart, ActionListener save, ActionListener next, ActionListener undo, ActionListener home){

        buttons[0].addActionListener(restart);
        buttons[1].addActionListener(save);
        buttons[2].addActionListener(next);
        buttons[3].addActionListener(undo);
        buttons[4].addActionListener(home);

    }

}
