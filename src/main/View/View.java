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

        JLabel title_text = new JLabel("Select a Configuration");
        title_text.setFont(new Font("Agency FB", Font.BOLD, 38));
        title_text.setForeground(Color.white);
        title_text.setBounds(50, 10, 500, 60);

        mainPane.add(title_text);
        mainPane.add(start);

    }

    public void initGame(Rectangle[] position) {

        mainPane.removeAll();
        mainPane.setIcon(null);

        buttons = new Buttons();
        board = new Board();

        board.setPositions(position);

        buttons.setBounds(420, 10, 100, 500);
        mainPane.add(buttons);

        board.setBounds(10, 10, 400, 700);
        mainPane.add(board);

    }


    public void restart(Rectangle[] initialPositions){
        board.setPositions(initialPositions);
        selectBlock(null);
        setDisplayedCounter(0);
    }

    public void winMessage() {
        JOptionPane.showMessageDialog(board, "You won!", "WIN", JOptionPane.INFORMATION_MESSAGE);
    }

    public void selectBlock(Component selected) {
        board.highlightSelected((Block) selected);
    }

    public void selectBlock(int x, int y){
        board.selectBlock(new Point(x,y));
    }

    public void moveSelectedBlock(Rectangle newPos){
        board.moveSelectedBlock(newPos);
    }

    public void setDisplayedCounter(int step) {
        board.setDisplayedCounter(step);
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


}