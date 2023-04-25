import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//no hardcoded variables!!!

public class Piece {
    Rectangle position;
    Rectangle[] availableMoves = new Rectangle[4];
    JLabel appearance;
    Border border = BorderFactory.createLineBorder(Color.BLUE);

    Piece(Rectangle intial_position, boolean type){

        position = intial_position;
        appearance = new JLabel();
        appearance.setBorder(border);
        appearance.setOpaque(true);
        appearance.setVisible(true);
        updateAvailable();

        appearance.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Board.selectPiece(Piece.this);

            }
        });

        appearance.setBounds(position);

        //si setta il pezzo rosso
        if (type)
            appearance.setBackground(Color.red);
        else
            appearance.setBackground(Color.black);
    }

    public JLabel getAppearance() {
        return appearance;
    }

    private void updateAvailable(){

        for(int i=0; i<4; i++)
            availableMoves[i] = new Rectangle(position);

        availableMoves[0].translate(-100,0);
        availableMoves[1].translate(+100,0);
        availableMoves[2].translate(0,+100);
        availableMoves[3].translate(0,-100);

    }

    public void move(Rectangle newPos){
        position = newPos;
        appearance.setBounds(position);
        updateAvailable();
    }

    public boolean intersection(Rectangle newPos){
        return position.intersects(newPos);
    }

    public Rectangle checkAvailable(Point p){

        for(Rectangle available : availableMoves){
            if(available.contains(p))
                return available;
        }

        return position;
    }

}
