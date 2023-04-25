import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//no hardcoded variables!!!

public class Piece {
    Rectangle position;
    Rectangle[] availableMoves = new Rectangle[4];
    JLabel appearance;
    BufferedImage myPicture;
//    Border border = BorderFactory.createLineBorder(Color.BLUE);

    Piece(Rectangle initial_position, boolean type) throws IOException {

//        try {
//            BufferedImage myPicture = ImageIO.read(new File("./src/1x1.png"));
//            appearance = new JLabel(new ImageIcon(myPicture));
//        } catch(Exception e) {
//            System.out.println("import delle immagini fallito");
//            return;
//        }
        position = initial_position;
        setImage();
        appearance = new JLabel(new ImageIcon(myPicture));


//        appearance.setBorder(border);
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
        if (type) {
            appearance.setBackground(Color.red);
        } else {
            appearance.setBackground(Color.black);
        }
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

    public void move(Rectangle newPos) {
        position = newPos;
        appearance.setBounds(position);
        updateAvailable();
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

        if (position.width == 100 && position.height == 100) {
            myPicture = ImageIO.read(new File("./src/1x1.png"));
        }

        if (position.width == 100 && position.height == 200) {
            myPicture = ImageIO.read(new File("./src/1x2.png"));
        }

        if (position.width == 200 && position.height == 100) {
            myPicture = ImageIO.read(new File("./src/2x1.png"));
        }

        if (position.width == 200 && position.height == 200) {
            myPicture = ImageIO.read(new File("./src/2x2.png"));
        }

    }

    public void setBorder(boolean on){
        if (on)
            appearance.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            appearance.setBorder(null);



    }

}
