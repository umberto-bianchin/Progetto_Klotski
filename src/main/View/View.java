package View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View {

    JFrame frame = new JFrame("KLOTSKI's PUZZLE");

    Board board;
    Buttons buttons;
    JPanel mainPane;



    public View() throws IOException {



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(mainPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(550,620));

        initUI();

        frame.add(mainPane);


    }

    public void initUI(){
        mainPane = new JPanel();
        buttons = new Buttons();
        board = new Board();

        buttons.setBounds(420,10,100,500);
        mainPane.add(buttons);

        board.setBounds(10,10,400,700);
        mainPane.add(board);

        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);

    }

    public Board getBoard(){
        return board;
    }

    public void winMessage(){
        JOptionPane.showMessageDialog(getBoard(), "Hai vinto!", "VITTORIA", JOptionPane.INFORMATION_MESSAGE);
    }

    public Buttons getButtons(){
        return buttons;
    }

    public void destroy(){
        //mainPane.setVisible(false);

        //frame.removeAll();
        frame.remove(mainPane);
        initUI();
        frame.add(mainPane);
        frame.revalidate();
        //frame.invalidate();
        //frame.validate();
        frame.repaint();

    }
}
