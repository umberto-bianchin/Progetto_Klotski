import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class Board extends JPanel {

    static Piece selectedPiece;
    Piece[] pieces = new Piece[10];

    Board() throws IOException {
        setLayout(null);
//        setMaximumSize(new Dimension(400,500));


        //ImageIcon logo = new ImageIcon("logo.png");

        //si settano tutti i pezzi nell'ordine dell'immagine delle slide del progetto
        pieces[0] = new Piece(new Rectangle(0, 0, 100, 200), false);
        pieces[1] = new Piece(new Rectangle(0, 200, 100, 200), false);
        pieces[2] = new Piece(new Rectangle(0, 400, 100, 100), false);
        pieces[3] = new Piece(new Rectangle(100, 0, 200, 200), true);
        pieces[4] = new Piece(new Rectangle(100, 200, 200, 100), false);
        pieces[5] = new Piece(new Rectangle(100, 300, 100, 100), false);
        pieces[6] = new Piece(new Rectangle(200, 300, 100, 100), false);
        pieces[7] = new Piece(new Rectangle(300, 0, 100, 200), false);
        pieces[8] = new Piece(new Rectangle(300, 200, 100, 200), false);
        pieces[9] = new Piece(new Rectangle(300, 400, 100, 100), false);

        for (Piece piece : pieces) {
            add(piece.getAppearance());
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

    private void move(MouseEvent e) {

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

            selectedPiece.move(possiblePosition);
            selectedPiece.setBorder(false);
            selectedPiece = null;
        }

    }
}