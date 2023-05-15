package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Buttons extends JPanel {

    private final JButton[] buttons = new JButton[5];

     Buttons(){

        setBackground(Color.white);
        setLayout(null);
        setBounds(425, 10, 100, 700);
        setOpaque(false);

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
            button.setContentAreaFilled(false);
            button.setBackground(null);
            add(button);
        }
    }

    public void addButtonListener(MouseAdapter restart, MouseAdapter save, MouseAdapter next, MouseAdapter undo, MouseAdapter home){

        buttons[0].addMouseListener(restart);
        buttons[1].addMouseListener(save);
        buttons[2].addMouseListener(next);
        buttons[3].addMouseListener(undo);
        buttons[4].addMouseListener(home);

    }

}
