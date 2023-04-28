package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Controller.*;

public class Buttons extends JPanel {

    private final JButton[] buttons = new JButton[4];

    public Buttons(){

        setBackground(Color.white);
        setLayout(null);

        buttons[0] = new JButton(new ImageIcon("./src/images/restart.png"));
        buttons[1] = new JButton(new ImageIcon("./src/images/save.png"));
        buttons[2] = new JButton(new ImageIcon("./src/images/next.png"));
        buttons[3] = new JButton(new ImageIcon("./src/images/undo.png"));

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

    public void addCommand(ActionListener[] command){

        for(int i=0; i<4; i++) {
            buttons[i].addActionListener(command[i]);
        }

    }

}
