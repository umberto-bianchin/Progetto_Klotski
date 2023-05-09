package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

public class View {

    Board board;
    Buttons buttons;
    JFrame frame = new JFrame("KLOTSKI's PUZZLE");
    JLabel mainPane = new JLabel();
    Start start = new Start();
    Authentication authentication = new Authentication(frame);

    public View() {

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
        mainPane.setOpaque(true);

        JLabel title_text = new JLabel("Select a Configuration", SwingConstants.CENTER);
        title_text.setFont(new Font("Agency FB", Font.BOLD, 38));
        title_text.setForeground(Color.white);
        title_text.setBounds(0, 20, 550, 50);

        mainPane.add(title_text);
        mainPane.add(start);
        mainPane.add(authentication);
        mainPane.revalidate();

    }

    public void initGame(Rectangle[] position, int counter) {

        mainPane.removeAll();
        mainPane.setIcon(null);

        buttons = new Buttons();
        board = new Board(counter);

        board.setPositions(position);

        buttons.setBounds(420, 10, 100, 700);
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

}