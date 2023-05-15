package View;

import Model.Move;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class KlotskiUI {

    Board board;
    Buttons buttons;
    JFrame frame;
    JLabel mainPane;
    Start start;
    Authentication authentication;

    /**
     * Create the frame and the mainPane, display the credit screen. Create but not show the Auth and Configuration panel
     */
    public KlotskiUI() {

        FlatDarculaLaf.setup();
        frame = new JFrame("KLOTSKI's PUZZLE");

        mainPane = new JLabel();
        mainPane.setIcon(new ImageIcon("./src/images/loading.png"));
        start = new Start();
        authentication = new Authentication(frame);

        frame.add(mainPane);

        buttons = new Buttons();
        board = new Board();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(550, 620));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

    /**
     * Display the configuration chooser and the Auth panel
     */
    public void initStart() {

        mainPane.removeAll();
        mainPane.setIcon(new ImageIcon("./src/images/background.png"));

        JLabel title_text = new JLabel("Select a Configuration", SwingConstants.CENTER);
        title_text.setFont(new Font("Agency FB", Font.BOLD, 38));
        title_text.setForeground(Color.white);
        title_text.setBounds(0, 20, 550, 50);

        mainPane.add(title_text);
        mainPane.add(start);
        mainPane.add(authentication);
        mainPane.revalidate();

    }

    /**
     * Display Blocks and Buttons needed for the Game
     * @param position Blocks positions
     * @param counter moves counter
     */
    public void initGame(Rectangle[] position, int counter) {

        mainPane.removeAll();
        String os = System.getProperty("os.name").toLowerCase();
        String imagePath = os.contains("win") ? "./src/images/background2win.png" : "./src/images/background2mac.png";
        mainPane.setIcon(new ImageIcon(imagePath));

        board.setDisplayedCounter(counter);
        board.setPositions(position);

        mainPane.add(buttons);
        mainPane.add(board);

    }

    /**
     * Restart the UI to the specified positions
     * @param initialPositions initial Blocks positions
     */
    public void restart(Rectangle[] initialPositions) {
        board.setPositions(initialPositions);
        board.highlightSelected(null);
        board.setDisplayedCounter(0);
    }

    /**
     * Make the specified move
     * @param counter moves counter
     */
    public void makeMove(Move lastMove, int counter) {
        board.selectBlock(lastMove.getInitialPosition().getLocation());
        board.moveSelectedBlock(lastMove.getFinalPosition(), counter);
    }

    /**
     *
     * @param selected Block desired to get selected
     */
    public void selectBlock(Component selected) {
        board.highlightSelected((Block) selected);
    }

    /**
     * Display the player name, in the authentication pane
     * @param user string with the username of the logged player
     */
    public void initUser(String user){
        authentication.initUser(user);
    }

    public void logout() {
        authentication.initAuthentication();
    }

    /**
     * @param savedGamesNames string representing the saved games of the logged user
     * @param listener that handle the click in the button with a saved game
     */
    public void showSavedGames(Vector<String> savedGamesNames, MouseAdapter listener) {
        SavedGamesDialog savedGames = new SavedGamesDialog(frame, listener, savedGamesNames);
        savedGames.setVisible(true);
    }

    /**
     * Input dialog asking the desired game name to save the match
     * @return null if closed, or a string with the typed name
     */
    public String askGameName() {
        return JOptionPane.showInputDialog(frame, "Choose the game name: ", "Save", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * @param message Desired message to display
     * @param title title of the pop-up windows
     * @param type type of message to be displayed - ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, or PLAIN_MESSAGE (0-4)
     */
    public void showMessage(String message, String title, int type){
        if(message == null || message.isBlank())
            return;

        if(message.contains("SQL")) {
            JOptionPane.showMessageDialog(mainPane, "Database error, retry later", title, type);
            return;
        }

        JOptionPane.showMessageDialog(mainPane, message, title, type);
    }

    /**
     *
     * @return true if the user choose to Authenticate or false if not
     */
    public boolean showAuthenticationDialog(){

        Object[] options = {"Log in", "Sign up", "Back"};

        int n = JOptionPane.showOptionDialog(frame,
                "You can't save a game without being authenticated ",
                "Error Saving Match",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[2]);

        // 2 -> Back, -1 -> closed windows
        if(n == 2 || n == -1)
            return false;

        authentication.showAuthenticationDialog(frame, (String) options[n]);
        return true;
    }

    public void addGameBoardListeners(MouseAdapter whiteBoard, MouseAdapter block){
        board.addMouseListener(whiteBoard);
//        board.addLis(whiteBoard);
        board.addBlockListener(block);
    }

    public void addStartListener(WindowAdapter close, MouseAdapter configuration) {
        frame.addWindowListener(close);
        start.addConfigurationListener(configuration);
    }

    public void addButtonsListeners(MouseAdapter restart, MouseAdapter save, MouseAdapter next, MouseAdapter undo, MouseAdapter home) {
        buttons.addButtonListener(restart, save, next, undo, home);
    }

    public void addAuthenticationListeners(MouseAdapter auth, MouseAdapter logOut, MouseAdapter saved){
        authentication.addAuthenticationListeners(auth, logOut, saved);
    }


}