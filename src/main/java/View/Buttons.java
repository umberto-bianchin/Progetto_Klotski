package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * The Buttons class represents a panel containing the set of game buttons
 */
public class Buttons extends JPanel {

    private final JButton[] buttons = new JButton[5];

    public Buttons() {
        setBackground(Color.white);
        setLayout(null);
        setBounds(425, 10, 100, 700);
        setOpaque(false);

        createAndPositionButtons();
    }

    /**
     * Creates and positions the buttons on the panel
     */
    private void createAndPositionButtons() {
        String[] buttonIcons = {
                "/images/restart.png",
                "/images/save.png",
                "/images/next.png",
                "/images/undo.png",
                "/images/home.png"
        };

        int y = 0;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = createButton(buttonIcons[i], y);
            y += 110;
        }
    }

    /**
     * Creates a button with the specified icon and position
     * @param iconPath The path to the button's icon
     * @param y The y-coordinate of the button's position
     * @return The created JButton
     */
    private JButton createButton(String iconPath, int y) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));
        button.setBounds(0, y, 100, 100);
        button.setContentAreaFilled(false);
        add(button);
        return button;
    }

    /**
     * Adds the button listeners for the different buttons
     * @param restart The MouseAdapter for the restart button
     * @param save The MouseAdapter for the save button
     * @param next The MouseAdapter for the next button
     * @param undo The MouseAdapter for the undo button
     * @param home The MouseAdapter for the home button
     */
    public void addButtonListener(MouseAdapter restart, MouseAdapter save, MouseAdapter next, MouseAdapter undo, MouseAdapter home) {
        buttons[0].addMouseListener(restart);
        buttons[1].addMouseListener(save);
        buttons[2].addMouseListener(next);
        buttons[3].addMouseListener(undo);
        buttons[4].addMouseListener(home);
    }
}
