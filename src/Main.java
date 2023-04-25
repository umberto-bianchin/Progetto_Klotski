import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("KLOTSKI's PUZZLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Board());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(415,540));
        frame.setLocationRelativeTo(null);

    }
}