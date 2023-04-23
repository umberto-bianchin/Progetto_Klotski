import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Board extends JPanel implements MouseListener {

    Piece[] pieces = new Piece[10];

    Board(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //ImageIcon logo = new ImageIcon("logo.png");

        //si settano tutti i pezzi nell'ordine dell'immagine delle slide del progetto
        pieces[0] = new Piece(new Position(0,0,1,2), false);
        pieces[1] = new Piece(new Position(0,2,1,2), false);
        pieces[2] = new Piece(new Position(0,4,1,1), false);
        pieces[3] = new Piece(new Position(1,0,2,2), true);
        pieces[4] = new Piece(new Position(1,2,2,1), false);
        pieces[5] = new Piece(new Position(1,3,1,1), false);
        pieces[6] = new Piece(new Position(2,3,1,1), false);
        pieces[7] = new Piece(new Position(3,0,1,2), false);
        pieces[8] = new Piece(new Position(3,2,1,2), false);
        pieces[9] = new Piece(new Position(3,4,1,1), false);

        for(int i=0; i<pieces.length; i++) {
            add(pieces[i].getAppearance(), pieces[i].getLayout(gbc));
            pieces[i].getAppearance().addMouseListener(this);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX()/100);
                System.out.println(e.getY()/100);
                System.out.println();

            }
        });

    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("click");
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}