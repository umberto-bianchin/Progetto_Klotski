package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Piece extends JLabel{
    private Rectangle position;
    private final Rectangle[] availableMoves = new Rectangle[4];
//    private final JLabel appearance;
    private BufferedImage myPicture;
    private final Rectangle FINISH_POSITION = new Rectangle(100, 300, 200, 200);


    public Piece(Rectangle initial_position) throws IOException {

        position = initial_position;
        setImage();

//        appearance = new JLabel(new ImageIcon(myPicture));
        setBounds(position);

        updateAvailable();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Board.selectPiece(Piece.this);
            }
        });
    }


    public void addListener(MouseAdapter listener){
        this.addMouseListener(listener);
    }


    private void updateAvailable() {

        for (int i = 0; i < 4; i++)
            availableMoves[i] = new Rectangle(position);

        availableMoves[0].translate(-100, 0);
        availableMoves[1].translate(+100, 0);
        availableMoves[2].translate(0, +100);
        availableMoves[3].translate(0, -100);

    }

    public boolean move(Rectangle newPos) {
        position = newPos;
        setBounds(position);
        updateAvailable();

        return position.contains(FINISH_POSITION);
    }

    public boolean intersection(Rectangle newPos) {
        return position.intersects(newPos);
    }

    public Rectangle checkAvailable(Point p) {

        for (Rectangle available : availableMoves) {
            if (available.contains(p)) {
                return available;
            }
        }
        return position;
    }

    private void setImage() throws IOException {

       myPicture = ImageIO.read(new File("./src/images/"+position.width+"x"+position.height+".png"));
       setIcon(new ImageIcon(myPicture));

/*        Claudia Prova a commentare quella sopra e scommentare quelle sotto
        URL file_path = getClass().getResource("/images/"+position.width+"x"+position.height+".png");

        try{
            myPicture = ImageIO.read(new File(file_path.toURI()));
        }
        catch(Exception e){} */

    }

    public void setBorder(boolean on){
        if (on)
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            setBorder(null);
    }


}
