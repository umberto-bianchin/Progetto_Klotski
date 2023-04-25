import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//no hardcoded variables!!!

public class Piece {
    Rectangle position;
    JLabel appearance;
    Border border = BorderFactory.createLineBorder(Color.BLUE);

    Piece(Rectangle intial_position, boolean type){

        position = intial_position;
        appearance = new JLabel();
        appearance.setBorder(border);
        appearance.setOpaque(true);
        appearance.setVisible(true);

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


    public boolean move(int new_x, int new_y){
        getNewPosition(new_x, new_y);
        return true;
    }


    private void getNewPosition(int new_x, int new_y){

        int x = new_x*100>position.x ? 100 : -100;
        int y = new_y*100>position.y ? 100 : -100;

        if(new_x*100 == position.x)
            x = 0;
        if(new_y*100 == position.y)
            y = 0;

        position.translate(x,y);
        appearance.setBounds(position);

    }
}
