package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * The Start class represents a panel displaying a set of buttons for game configuration (initial blocks position)
 */
class Start extends JPanel {

    private final JButton[] buttons = new JButton[4];

    public Start() {
        setLayout(new GridLayout(2, 2));
        setOpaque(false);
        setBounds(75, 80, 400, 440);

        for (int i = 0; i < 4; i++) {

            buttons[i] = new JButton(new ImageIcon("./src/images/miniatura" + i + ".png"));
            buttons[i].setName(String.valueOf(i));
            buttons[i].setContentAreaFilled(false);

            add(buttons[i]);
        }
    }

    /**
     * Adds a configuration listener to the buttons
     * @param listener The MouseAdapter for configuration events
     */
    public void addConfigurationListener(MouseAdapter listener) {
        for (JButton button : buttons)
            button.addMouseListener(listener);
    }
}
