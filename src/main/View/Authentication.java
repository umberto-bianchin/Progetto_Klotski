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
    private AuthenticationDialog auth;

    public Authentication(JFrame frame){
        setOpaque(false);
        setBounds(0,550,550,50);

        initAuthentication();

        ActionListener listener = e -> {
            auth = new AuthenticationDialog(frame, ((JButton) e.getSource()).getClientProperty("name").toString(), authListener);
            auth.setVisible(true);
        };

        log_in.addActionListener(listener);
        sign_up.addActionListener(listener);

        ImageIcon signup = new ImageIcon("./src/images/signup.png");
        sign_up.setIcon(signup);
        sign_up.setBorder(null);
        sign_up.setContentAreaFilled(false);
        sign_up.setBackground(null);
        sign_up.putClientProperty("name", "Sign up");

        ImageIcon login = new ImageIcon("./src/images/login.png");
        log_in.setIcon(login);
        log_in.setBorder(null);
        log_in.setContentAreaFilled(false);
        log_in.setBackground(null);
        log_in.setName("Log in");
        log_in.putClientProperty("name", "Log in");

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


//        saved_games.setAlignmentX(RIGHT_ALIGNMENT);
//        log_out.setAlignmentX(RIGHT_ALIGNMENT);


        ImageIcon logout = new ImageIcon("./src/images/logout.png");
        log_out.setIcon(logout);
        log_out.setBorder(null);
        log_out.setContentAreaFilled(false);
        log_out.setBackground(null);

        ImageIcon savedGames = new ImageIcon("./src/images/savedGames.png");
        saved_games.setIcon(savedGames);
        saved_games.setBorder(null);
        saved_games.setContentAreaFilled(false);
        saved_games.setBackground(null);

//        setBounds(0,550,550,50);
//        setBounds(220,550,350,50);
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

