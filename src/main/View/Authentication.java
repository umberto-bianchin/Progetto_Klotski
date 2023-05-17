package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * The Authentication class represents a custom JPanel used for handling authentication
 * and user interface components related to authentication (log_out/delete user and showing saved games)
 */
class Authentication extends JPanel{

    private final JButton sign_up = new JButton(new ImageIcon("./src/images/signup.png"));
    private final JButton log_in = new JButton(new ImageIcon("./src/images/login.png"));
    private final JButton log_out = new JButton(new ImageIcon("./src/images/logout.png"));
    private final JButton del_user = new JButton(new ImageIcon("./src/images/deleteUser.png"));
    private final JButton saved_games = new JButton(new ImageIcon("./src/images/savedGames.png"));
    private static MouseAdapter authListener;

    /**
     * @param frame The JFrame parents of the Authentication JPanel
     */
    public Authentication(JFrame frame){
        setOpaque(false);
        setBounds(0,530,550,70);

        initAuthentication();

        ActionListener listener = e -> showAuthenticationDialog(frame, ((JButton) e.getSource()).getClientProperty("name").toString());

        log_in.addActionListener(listener);
        sign_up.addActionListener(listener);

        sign_up.setContentAreaFilled(false);
        sign_up.putClientProperty("name", "Sign up");

        log_in.setContentAreaFilled(false);
        log_in.putClientProperty("name", "Log in");

    }

    /**
     * Shows the authentication dialog with the specified JFrame as a parent and authentication type
     * @param frame The JFrame to associate the authentication dialog with
     * @param type The authentication type: "Sign up" or "Log in"
     */
    public void showAuthenticationDialog(JFrame frame, String type){
        AuthenticationDialog auth = new AuthenticationDialog(frame, type, authListener);
        auth.setVisible(true);
    }

    /**
     * Initializes the authentication UI by adding sign-up and log-in buttons
     */
    public void initAuthentication(){
        removeAll();
        add(sign_up);
        add(log_in);
        repaint();
        revalidate();
    }

    /**
     * Initializes the user interface after successful authentication by adding the user's name
     * log-out button, delete user button, and saved games button
     * @param user The name of the authenticated user
     */
    public void initUser(String user){
        removeAll();

        JLabel name = new JLabel(user);
        name.setFont(new Font("Agency FB", Font.BOLD, 20));
        name.setForeground(Color.WHITE);

        log_out.setContentAreaFilled(false);
        log_out.setName("logOut");

        del_user.setContentAreaFilled(false);
        del_user.setName("delUser");

        saved_games.setContentAreaFilled(false);

        add(name);
        add(log_out);
        add(saved_games);
        add(del_user);
        repaint();
        revalidate();
    }

    /**
     * Adds the authentication listeners to the associated components of the Authentication JPanel
     * @param authListener The authentication listener to handle authentication events (log_in/sign_up)
     * @param logOutListener The log-out listener to handle log-out events
     * @param savedListener The listener to handle events related to saved games
     */
    public void addAuthenticationListeners(MouseAdapter authListener, MouseAdapter logOutListener, MouseAdapter savedListener) {
        Authentication.authListener = authListener;
        log_out.addMouseListener(logOutListener);
        del_user.addMouseListener(logOutListener);
        saved_games.addMouseListener(savedListener);
    }
}
