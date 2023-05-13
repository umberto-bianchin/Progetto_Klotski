package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class Authentication extends JPanel{

    private final JButton sign_up = new JButton();
    private final JButton log_in = new JButton();
    private final JButton log_out = new JButton();
    private final JButton saved_games = new JButton();
    private static ActionListener authListener;

    public Authentication(JFrame frame){
        setOpaque(false);
        setBounds(0,550,550,50);

        initAuthentication();

        ActionListener listener = e -> showAuthenticationDialog(frame, ((JButton) e.getSource()).getClientProperty("name").toString());


        log_in.addActionListener(listener);
        sign_up.addActionListener(listener);

        sign_up.setIcon(new ImageIcon("./src/images/signup.png"));
        sign_up.setBorder(null);
        sign_up.setContentAreaFilled(false);
        sign_up.setBackground(null);
        sign_up.putClientProperty("name", "Sign up");

        log_in.setIcon(new ImageIcon("./src/images/login.png"));
        log_in.setBorder(null);
        log_in.setContentAreaFilled(false);
        log_in.setBackground(null);
        log_in.setName("Log in");
        log_in.putClientProperty("name", "Log in");

    }

    public void showAuthenticationDialog(JFrame frame, String type){

        AuthenticationDialog auth = new AuthenticationDialog(frame, type, authListener);
        auth.setVisible(true);

    }


    public void initAuthentication(){
        removeAll();
        setBounds(0,550,550,50);

        add(sign_up);
        add(log_in);
        repaint();
        revalidate();
    }

    public void initUser(String user){
        removeAll();
        setBounds(0,550,550,50);

//        setAlignmentX(RIGHT_ALIGNMENT);

        JLabel name = new JLabel(user);
        name.setFont(new Font("Agency FB", Font.BOLD, 20));
        name.setForeground(Color.WHITE);

        log_out.setIcon(new ImageIcon("./src/images/logout.png"));
        log_out.setBorder(null);
        log_out.setContentAreaFilled(false);
        log_out.setBackground(null);

        saved_games.setIcon(new ImageIcon("./src/images/savedGames.png"));
        saved_games.setBorder(null);
        saved_games.setContentAreaFilled(false);
        saved_games.setBackground(null);

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

