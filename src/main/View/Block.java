package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;


class Block extends JLabel{

    public void setBlockAppearance(Rectangle position){
        setImage(position);
        setBounds(position);
    }

    public void addListener(MouseAdapter listener){
        this.addMouseListener(listener);

    }

    private void setImage(Rectangle initial_position) {
        ImageIcon myPicture = new ImageIcon("./src/images/" + initial_position.width + "x" + initial_position.height + ".png");
        setIcon(myPicture);
    }

    public void setBorder(boolean on){
        if (on)
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            setBorder(null);
    }
}
