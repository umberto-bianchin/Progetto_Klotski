package View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View {

    Board board = new Board();

    public View() throws IOException {

        JPanel mainPane = new JPanel();
        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);

        Buttons buttons = new Buttons();
        buttons.setBounds(410,0,150,550);
        mainPane.add(buttons);

        board.setBounds(10,10,400,700);
        mainPane.add(board);

        JFrame frame = new JFrame("KLOTSKI's PUZZLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(550,620));


    }

    public Board getBoard(){
        return board;
    }

    public void winMessage(){
        JOptionPane.showMessageDialog(getBoard(), "Hai vinto!", "VITTORIA", JOptionPane.INFORMATION_MESSAGE);
    }

}
