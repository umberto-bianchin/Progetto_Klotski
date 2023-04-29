package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;

public class View {

    JFrame frame = new JFrame("KLOTSKI's PUZZLE");

    Board board;
    Buttons buttons;
    JPanel mainPane;

    public View() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(550, 620));

        initUI();
        frame.add(mainPane);
    }

    public void initUI() {
        mainPane = new JPanel();
        buttons = new Buttons();
        board = new Board();

        buttons.setBounds(420, 10, 100, 500);
        mainPane.add(buttons);

        board.setBounds(10, 10, 400, 700);
        mainPane.add(board);

        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);
    }

    public Board getBoard() {
        return board;
    }

    public void winMessage() {
        JOptionPane.showMessageDialog(getBoard(), "Hai vinto!", "VITTORIA", JOptionPane.INFORMATION_MESSAGE);
    }

    public Buttons getButtons() {
        return buttons;
    }

    public void selectBlock(Block selected) {
        board.highlightSelected(selected);
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

    public void setPositionBlocks(Rectangle[] position) throws IOException {
        board.setPositions(position);
    }

    public Block[] getBlocksRepresentation() {
        return board.getBlocksRepresentation();
    }

}