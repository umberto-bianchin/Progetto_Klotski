package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The AuthenticationDialog class represents a custom JDialog used for displaying an authentication dialog
 */
public class AuthenticationDialog extends JDialog {

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    GridBagConstraints cs = new GridBagConstraints();


    /**
     * Constructs an AuthenticationDialog object with the specified parent frame, title, and listener
     * @param parent The parent frame associated with the dialog
     * @param title The title of the JDialog
     * @param listener The listener to handle mouse events for the confirm button
     */
    public AuthenticationDialog(Frame parent, String title, MouseAdapter listener) {
        super(parent, title, true);

        JPanel panel = createPanel();
        createFields(panel);
        createButtons(panel, listener);

        add(panel);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }


    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        cs.fill = GridBagConstraints.HORIZONTAL;
        return panel;
    }

    /**
     * Creates the username and password fields and adds them to the panel.
     * @param panel The panel to which the fields will be added.
     */
    private void createFields(JPanel panel) {

        JLabel user = new JLabel("  Username: ");
        panel.add(user, cs);

        JLabel pass = new JLabel("  Password: ");
        cs.gridy = 1;
        panel.add(pass, cs);

        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(usernameField, cs);

        cs.gridy = 1;
        panel.add(passwordField, cs);
    }

    /**
     * Creates the confirm and cancel buttons and adds them to the panel.
     * @param panel The panel to which the buttons will be added.
     * @param listener The listener to handle mouse events for the confirm button.
     */
    private void createButtons(JPanel panel, MouseAdapter listener) {

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(cancelButton, cs);

        JButton confirmButton = new JButton();
        confirmButton.setText(getTitle());
        cs.gridx = 2;
        panel.add(confirmButton, cs);

        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                confirmButton.putClientProperty("username", usernameField.getText().trim());
                confirmButton.putClientProperty("password", new String(passwordField.getPassword()));
                dispose();
                listener.mousePressed(e);
            }
        });

    }
}
