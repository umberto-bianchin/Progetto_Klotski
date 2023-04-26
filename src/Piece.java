import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Piece {
    private Rectangle position;
    private final Rectangle[] availableMoves = new Rectangle[4];
    private final JLabel appearance;
    private BufferedImage myPicture;
    private final Rectangle FINISH_POSITION = new Rectangle(100, 300, 200, 200);


    Piece(Rectangle initial_position) throws IOException {

        position = initial_position;
        setImage();
        appearance = new JLabel(new ImageIcon(myPicture));
        appearance.setBounds(position);

        updateAvailable();

        appearance.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Board.selectPiece(Piece.this);
            }
        });
    }

    public JLabel getAppearance() {
        return appearance;
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
        appearance.setBounds(position);
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

/*        Claudia Prova a commentare quella sopra e scommentare quelle sotto
        URL file_path = getClass().getResource("/images/"+position.width+"x"+position.height+".png");

        try{
            myPicture = ImageIO.read(new File(file_path.toURI()));
        }
        catch(Exception e){} */

    }

    public void setBorder(boolean on){
        if (on)
            appearance.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            appearance.setBorder(null);
    }
}
