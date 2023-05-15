package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

class Authentication extends JPanel{

    private final JButton sign_up = new JButton();
    private final JButton log_in = new JButton();
    private final JButton log_out = new JButton();
    private final JButton del_user = new JButton();

    private final JButton saved_games = new JButton();
    private static MouseAdapter authListener;

    public Authentication(JFrame frame){
        setOpaque(false);
        setBounds(0,550,550,50);

        initAuthentication();

        ActionListener listener = e -> showAuthenticationDialog(frame, ((JButton) e.getSource()).getClientProperty("name").toString());


        log_in.addActionListener(listener);
        sign_up.addActionListener(listener);

        sign_up.setIcon(new ImageIcon("./src/images/signup.png"));
        sign_up.setContentAreaFilled(false);
        sign_up.putClientProperty("name", "Sign up");

        log_in.setIcon(new ImageIcon("./src/images/login.png"));
        log_in.setContentAreaFilled(false);
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

        JLabel name = new JLabel(user);
        name.setFont(new Font("Agency FB", Font.BOLD, 20));
        name.setForeground(Color.WHITE);

        log_out.setIcon(new ImageIcon("./src/images/logout.png"));
        log_out.setContentAreaFilled(false);
        log_out.setName("logOut");


        del_user.setIcon(new ImageIcon("./src/images/deleteUser.png"));
        del_user.setContentAreaFilled(false);
        del_user.setName("delUser");


        saved_games.setIcon(new ImageIcon("./src/images/savedGames.png"));
        saved_games.setContentAreaFilled(false);

        add(name);
        add(log_out);
        add(saved_games);
        add(del_user);
        repaint();
        revalidate();
    }

    public void addAuthenticationListeners(MouseAdapter authListener, MouseAdapter logOutListener, MouseAdapter savedListener) {
        Authentication.authListener = authListener;
        log_out.addMouseListener(logOutListener);
        del_user.addMouseListener(logOutListener);
        saved_games.addMouseListener(savedListener);
    }

}

