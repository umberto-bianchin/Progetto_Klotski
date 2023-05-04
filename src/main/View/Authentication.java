package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Authentication extends JPanel{

    private final JButton sign_up = new JButton("Sign up");
    private final JButton log_in = new JButton("Log in");
    private final JButton log_out = new JButton("Log out");
    private final JButton saved_games = new JButton("Saved games");
    private static ActionListener authListener;
    private AuthenticationDialog auth;

    public Authentication(){
        setOpaque(false);
        initAuthentication();

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                auth = new AuthenticationDialog(null, ((JButton)e.getSource()).getText(), authListener);
                auth.setVisible(true);
            }
        };

        log_in.addActionListener(listener);
        sign_up.addActionListener(listener);

    }

    public void initAuthentication(){
        removeAll();
        setBounds(350,550,200,50);
        add(sign_up);
        add(log_in);
        repaint();
        revalidate();
    }

    public void initUser(String user){
        removeAll();
        JLabel name = new JLabel(user);
        //SETTARE STILE SCRITTA
        name.setForeground(Color.WHITE);

        setBounds(220,550,350,50);
        add(name);
        add(log_out);
        add(saved_games);
        repaint();
        revalidate();
    }

    public void addAuthListener(ActionListener listener){
        authListener = listener;
    }
    public String[] getCredentials(){
        return new String[]{auth.getUsername(), auth.getPassword()};
    }

    public void showAuthResult(boolean authenticated){
        if (authenticated){
            JOptionPane.showMessageDialog(auth,
                    "Hi " + auth.getUsername() + "! You have successfully logged in.",
                    auth.getTitle(), JOptionPane.INFORMATION_MESSAGE);
            auth.dispose();
            initUser(auth.getUsername());
        } else {
            JOptionPane.showMessageDialog(auth,
                    "Invalid username or password",
                    auth.getTitle(),
                    JOptionPane.ERROR_MESSAGE);
            // reset username and password
            auth.resetText();
        }
    }

    public void addLogOutListener(ActionListener listener){
        log_out.addActionListener(listener);
    }

}

