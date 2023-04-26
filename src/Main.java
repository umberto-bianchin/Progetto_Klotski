import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        JPanel mainPane = new JPanel();
        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);

        Buttons buttons = new Buttons();
        buttons.setBounds(10,10,400,60);
        mainPane.add(buttons);

        Board board = new Board();
        board.setBounds(10,70,400,700);
        mainPane.add(board);


        JFrame frame = new JFrame("KLOTSKI's PUZZLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(440,700));

    }
}