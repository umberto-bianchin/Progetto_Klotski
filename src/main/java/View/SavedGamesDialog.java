package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Vector;

/**
 * A dialog for displaying saved games
 */
public class SavedGamesDialog extends JDialog {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints cs = new GridBagConstraints();
    MouseAdapter listener;

    /**
     * @param parent the parent frame
     * @param listener the mouse adapter listener to handle saving, deleting a match or delete all games
     * @param numberSavedGames the vector of saved game names
     */
    public SavedGamesDialog(Frame parent, MouseAdapter listener, Vector<String> numberSavedGames) {
        super(parent, "Saved games", true);
        this.listener = listener;
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(3, 10, 3, 10);

        if (numberSavedGames.isEmpty()) {
            JLabel message = new JLabel("No saved match to show");
            message.setFont(new Font("Agency FB", Font.PLAIN, 20));
            message.setForeground(Color.white);
            panel.add(message, cs);
        }
        else {
            JButton delAllButton = createButton("Delete All", "delAll", 0, numberSavedGames.size());
            delAllButton.setFont(new Font("Agency FB", Font.BOLD, 20));
            panel.add(delAllButton, cs);
        }

        for (int i = 0; i < numberSavedGames.size(); i++) {
            String gameName = numberSavedGames.get(i);
            addEntry(gameName, i);
        }

        setSize(350, 250);
        add(BorderLayout.CENTER, new JScrollPane(panel));

        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Creates a button with the specified text and mouse listener.
     * @param buttonText the text to display on the button
     * @return the created button
     */
    private JButton createButton(String buttonText, String nameProperty, int gridx, int gridy) {
        JButton button = new JButton(buttonText);
        button.setName(nameProperty);
        button.setForeground(Color.white);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose(); //close the windows
                listener.mousePressed(e);
            }
        });

        cs.gridx = gridx;
        cs.gridy = gridy;

        return button;
    }

    /**
     * Adds an entry (game button and delete button) to the panel.
     * @param name the name of the game
     * @param number the entry number
     */
    private void addEntry(String name, int number) {
        JButton gameButton = createButton("Match Name:   " + name, "game" + name, 0, number);
        gameButton.setHorizontalAlignment(SwingConstants.LEFT);
        gameButton.setFont(new Font("Agency FB", Font.PLAIN, 20));
        cs.gridy = number;
        cs.gridx = 0;
        cs.weightx = 1.0;
        panel.add(gameButton, cs);

        JButton deleteButton = createButton("", "delete" + name, 2, number);
        deleteButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/delete.png"))));
        deleteButton.setContentAreaFilled(false);
        cs.weightx = 0;
        panel.add(deleteButton, cs);
    }
}
