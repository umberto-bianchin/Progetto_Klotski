package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Board extends JPanel {

    private static Piece selectedPiece;
    private static int counter=0;
    private final Piece[] pieces = new Piece[10];
    private final JLabel displayedCounter;

    public Board() throws IOException {
        setLayout(null);
        setBackground(Color.white);

        //si settano tutti i pezzi nell'ordine dell'immagine delle slide del progetto
        pieces[0] = new Piece(new Rectangle(0, 0, 100, 200));
        pieces[1] = new Piece(new Rectangle(0, 200, 100, 200));
        pieces[2] = new Piece(new Rectangle(0, 400, 100, 100));
        pieces[3] = new Piece(new Rectangle(100, 0, 200, 200));
        pieces[4] = new Piece(new Rectangle(100, 200, 200, 100));
        pieces[5] = new Piece(new Rectangle(100, 300, 100, 100));
        pieces[6] = new Piece(new Rectangle(200, 300, 100, 100));
        pieces[7] = new Piece(new Rectangle(300, 0, 100, 200));
        pieces[8] = new Piece(new Rectangle(300, 200, 100, 200));
        pieces[9] = new Piece(new Rectangle(300, 400, 100, 100));

        JLabel line = new JLabel();
        line.setBackground(Color.red);
        line.setOpaque(true);
        line.setBounds(100,505,200,10);
        line.setSize(200, 10);
        add(line);

        displayedCounter = new JLabel("Step " + counter);
        displayedCounter.setHorizontalAlignment(JLabel.CENTER);
        displayedCounter.setBackground(Color.white);
        displayedCounter.setFont(new Font("Serif", Font.BOLD, 25));
        displayedCounter.setBounds(10,525,400,40);
        displayedCounter.setSize(400, 40);
        add(displayedCounter);


        for (Piece piece : pieces) {
            add(piece);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                move(e);
            }
        });

    }

    public static void selectPiece(Piece selected) {
        if (selectedPiece != null) {
            selectedPiece.setBorder(false);
        }
        selectedPiece = selected;
        selectedPiece.setBorder(true);

    }

    public void addListener(MouseAdapter listener){
        this.addMouseListener(listener);
    }

    public void move(MouseEvent e) {

        if (selectedPiece != null) {
            Point click = new Point(e.getX(), e.getY());
            Rectangle possiblePosition = selectedPiece.checkAvailable(click);
            Rectangle window = new Rectangle(0, 0, 400, 500);

            if (window.contains(possiblePosition)) {
                for (Piece piece : pieces) {
                    if (piece != selectedPiece && piece.intersection(possiblePosition)) {
                        return;
                    }
                }
            } else {
                return;
            }

            if(selectedPiece.move(possiblePosition)){
                JOptionPane.showMessageDialog(this,"Hai vinto!","VITTORIA", JOptionPane.INFORMATION_MESSAGE);
            }
            selectedPiece.setBorder(false);
            selectedPiece = null;
            counter++;
            displayedCounter.setText("Step "+ counter);


        }
    }

    public Piece[] getPieces() {
        return pieces;
    }
}