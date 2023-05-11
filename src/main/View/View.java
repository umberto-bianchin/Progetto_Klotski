package View;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

public class View {

    Board board;
    Buttons buttons;
    JFrame frame;
    JLabel mainPane = new JLabel();
    Start start = new Start();
    Authentication authentication;

    public View() {
        FlatDarculaLaf.setup();
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("KLOTSKI's PUZZLE");
        authentication = new Authentication(frame);
//        frame.getRootPane().putClientProperty("JRootPane.titleBarBackground", );
//        frame.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);

        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);
        mainPane.setIcon(new ImageIcon("./src/images/loading.png"));

        frame.add(mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(550, 620));
        frame.setResizable(false);

    }

    public void initStart() {
        mainPane.removeAll();

        ImageIcon background = new ImageIcon("./src/images/background.png");
        mainPane.setIcon(background);

        JLabel title_text = new JLabel("Select a Configuration", SwingConstants.CENTER);
        title_text.setFont(new Font("Agency FB", Font.BOLD, 38));
        title_text.setForeground(Color.white);
        title_text.setBounds(0, 20, 550, 50);
        title_text.setHorizontalTextPosition(SwingConstants.CENTER);

        mainPane.add(title_text);
        mainPane.add(start);
        mainPane.add(authentication);
        mainPane.revalidate();

    }

    public void initGame(Rectangle[] position, int counter) {

        mainPane.removeAll();
        ImageIcon background = new ImageIcon("./src/images/background2.png");
        mainPane.setIcon(background);

        buttons = new Buttons();
        board = new Board(counter);

        board.setPositions(position);
        board.setOpaque(false);
        buttons.setOpaque(false);

        buttons.setBounds(425, 10, 100, 700);
        mainPane.add(buttons);

        board.setBounds(10, 10, 400, 700);
        mainPane.add(board);

    }

    public void restart(Rectangle[] initialPositions) {
        board.setPositions(initialPositions);
        board.highlightSelected(null);
        board.setDisplayedCounter(0);
    }

    public void makeMove(Model.Move lastMove, int step) {
        board.highlightSelected(null);
        board.selectBlock(lastMove.getInitialPosition().getLocation());
        board.moveSelectedBlock(lastMove.getFinalPosition(), step);
    }

    public void selectBlock(Component selected) {
        board.highlightSelected((Block) selected);
    }

    public void moveSelectedBlock(Rectangle newPos, int count) {
        board.moveSelectedBlock(newPos, count);
    }

    public void logout() {
        authentication.initAuthentication();
    }

    public void showSavedGames(Vector<String> savedGamesNames, ActionListener listener) {
        SavedGamesDialog savedGames = new SavedGamesDialog(frame, listener, savedGamesNames);
        savedGames.setVisible(true);
    }

    public String askGameName() {
        return JOptionPane.showInputDialog(frame, "Choose the game name: ", "Save", JOptionPane.QUESTION_MESSAGE);
    }

    public void initUser(String user){
        authentication.initUser(user);
    }

    public void showMessage(String message, String title, int type){
        if(message.isBlank())
            return;
        if(message.contains("SQL"))
            message = "Database error, retry later";
        JOptionPane.showMessageDialog(mainPane, message, title, type);
    }

    public void addGameBoardListeners(MouseAdapter whiteBoard, MouseAdapter block){
        board.addListener(whiteBoard);
        board.addBlockListener(block);
    }

    public void addConfigurationListener(ActionListener listener) {
        start.addConfigurationListener(listener);
    }

    public void addButtonsListeners(ActionListener restart, ActionListener save, ActionListener next, ActionListener undo, ActionListener home) {
        buttons.addButtonListener(restart, save, next, undo, home);
    }

    public void addAuthenticationListeners(ActionListener auth, ActionListener logOut, ActionListener saved){
        authentication.addAuthenticationListeners(auth, logOut, saved);
    }

}