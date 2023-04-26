import javax.swing.*;
import java.awt.*;

public class Buttons extends JPanel {

    private JButton[] buttons = new JButton[4];
    public Buttons(){

        setBackground(Color.white);

        buttons[0] = new JButton("RESET");
        buttons[1] = new JButton("SAVE");
        buttons[2] = new JButton("NEXT");
        buttons[3] = new JButton("UNDO");

        for(JButton button : buttons){
            add(Box.createRigidArea(new Dimension(6, 0)));
            add(button);
        }

    }
}
