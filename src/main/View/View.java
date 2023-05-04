package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class View {

    Board board;
    Buttons buttons;
    JFrame frame = new JFrame("KLOTSKI's PUZZLE");
    JLabel mainPane = new JLabel();
    Start start = new Start();
    Authentication authentication = new Authentication();

    public View(){

        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);

        initStart();

        frame.add(mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(550, 620));
        frame.setResizable(false);

    }

    public void initStart(){
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

    }

    public void initGame(Rectangle[] position) {

        mainPane.removeAll();
        mainPane.setIcon(null);

        buttons = new Buttons();
        board = new Board();

        board.setPositions(position);

        buttons.setBounds(420, 10, 100, 700);
        mainPane.add(buttons);

        board.setBounds(10, 10, 400, 700);
        mainPane.add(board);

    }

    public void restart(Rectangle[] initialPositions){
        board.setPositions(initialPositions);
        board.highlightSelected(null);
        board.setDisplayedCounter(0);
    }

    public void undo(Rectangle initial_position, Point final_location, int step){
        board.highlightSelected(null);
        board.selectBlock(new Point(final_location));
        board.moveSelectedBlock(initial_position, step);
    }

    public void winMessage() {
        JOptionPane.showMessageDialog(board, "You won!", "WIN", JOptionPane.INFORMATION_MESSAGE);
    }

    public void selectBlock(Component selected) {
        board.highlightSelected((Block) selected);
    }

    public void moveSelectedBlock(Rectangle newPos, int count){
        board.moveSelectedBlock(newPos, count);
    }

    public void addBoardListener(MouseAdapter listener) {
        board.addListener(listener);
    }

    public void addBlockListener(MouseAdapter listener){
        board.addBlockListener(listener);
    }

    public void addConfigurationListener(ActionListener listener){
        start.addConfigurationListener(listener);
    }

    public void addButtonsListener(ActionListener[] listener){
        buttons.addButtonListener(listener);
    }

    public void addAuthListener(ActionListener listener){
        authentication.addAuthListener(listener);
    }

    public void addLogOutListener(ActionListener listener){
        authentication.addLogOutListener(listener);
    }


    public String[] getCredentials(){
        return authentication.getCredentials();
    }

    public void showAuthResult(boolean authenticated){
        authentication.showAuthResult(authenticated);
    }

    public void logout(){
        authentication.initAuthentication();
    }

}