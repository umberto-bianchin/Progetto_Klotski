package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class AuthenticationDialog extends JDialog {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton confirmButton = new JButton();

    public AuthenticationDialog(Frame parent, String title, ActionListener listener) {
        super(parent, title, true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel label1 = new JLabel("  Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(label1, cs);

        usernameField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(usernameField, cs);

        JLabel label2 = new JLabel("  Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(label2, cs);

        passwordField = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(passwordField, cs);

        confirmButton.setText(title);
        confirmButton.setForeground(Color.BLUE);

        confirmButton.addActionListener(listener);

        cs.gridx = 2;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(confirmButton, cs);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(cancelButton, cs);


        add(panel);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void resetText(){
        usernameField.setText("");
        passwordField.setText("");
    }
}