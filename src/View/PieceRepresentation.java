package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PieceRepresentation extends JLabel{


    public PieceRepresentation(Rectangle initial_position) throws IOException {

        setImage(initial_position);
        setBounds(initial_position);

    }


    public void addListener(MouseAdapter listener){
        this.addMouseListener(listener);
    }



    private void setImage(Rectangle initial_position) throws IOException {

        BufferedImage myPicture = ImageIO.read(new File("./src/images/" + initial_position.width + "x" + initial_position.height + ".png"));
       setIcon(new ImageIcon(myPicture));

    }

    public void setBorder(boolean on){
        if (on)
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            setBorder(null);
    }


}
