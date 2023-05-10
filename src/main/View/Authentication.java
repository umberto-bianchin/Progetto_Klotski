package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class Authentication extends JPanel{

    private final JButton sign_up = new JButton("Sign up");
    private final JButton log_in = new JButton("Log in");
    private final JButton log_out = new JButton("Log out");
    private final JButton saved_games = new JButton("Saved games");
    private static ActionListener authListener;
    private AuthenticationDialog auth;

    public Authentication(JFrame frame){
        setOpaque(false);
        initAuthentication();

        ActionListener listener = e -> {
            auth = new AuthenticationDialog(frame, ((JButton)e.getSource()).getName(), authListener);
            auth.setVisible(true);
        };

        log_in.addActionListener(listener);
        sign_up.addActionListener(listener);

        ImageIcon signup = new ImageIcon("./src/images/signup.png");
        sign_up.setIcon(signup);
        sign_up.setBorder(null);
        //sign_up.setName("Sign up");
        sign_up.putClientProperty("name", "Sign up");

        ImageIcon login = new ImageIcon("./src/images/login.png");
        log_in.setIcon(login);
        log_in.setBorder(null);
        log_in.setName("Log in");
        log_in.putClientProperty("name", "Log in");

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

        // TODO: 06/05/23 SETTARE STILE SCRITTA
        name.setForeground(Color.WHITE);

        setBounds(220,550,350,50);
        add(name);
        add(log_out);
        add(saved_games);
        repaint();
        revalidate();
    }

    public void addAuthenticationListeners(ActionListener authListener, ActionListener logOutListener, ActionListener savedListener) {
        Authentication.authListener = authListener;
        log_out.addActionListener(logOutListener);
        saved_games.addActionListener(savedListener);
    }

}

