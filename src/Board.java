import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Board extends JPanel {

    Piece[] pieces = new Piece[10];
    static Piece selectedPiece;

    Board(){
        setLayout(null);

        //ImageIcon logo = new ImageIcon("logo.png");

        //si settano tutti i pezzi nell'ordine dell'immagine delle slide del progetto
        pieces[0] = new Piece(new Rectangle(0,0,100,200), false);
        pieces[1] = new Piece(new Rectangle(0,200,100,200), false);
        pieces[2] = new Piece(new Rectangle(0,400,100,100), false);
        pieces[3] = new Piece(new Rectangle(100,0,200,200), true);
        pieces[4] = new Piece(new Rectangle(100,200,200,100), false);
        pieces[5] = new Piece(new Rectangle(100,300,100,100), false);
        pieces[6] = new Piece(new Rectangle(200,300,100,100), false);
        pieces[7] = new Piece(new Rectangle(300,0,100,200), false);
        pieces[8] = new Piece(new Rectangle(300,200,100,200), false);
        pieces[9] = new Piece(new Rectangle(300,400,100,100), false);

        for(int i=0; i<pieces.length; i++) {
            add(pieces[i].getAppearance());
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedPiece.move(e.getX()/100, e.getY()/100);
            }
        });
    }

    public static void selectPiece(Piece selected){
        selectedPiece = selected;
    }
}