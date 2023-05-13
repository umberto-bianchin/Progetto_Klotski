package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;


class Block extends JLabel{

    public void setBlockAppearance(Rectangle position){
        setImage(position);
        setBounds(position);
    }

    private void setImage(Rectangle initial_position) {
        setIcon(new ImageIcon("./src/images/" + initial_position.width + "x" + initial_position.height + ".png"));
    }

    public void setBorder(boolean on){
        if (on)
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            setBorder(null);
    }

    public void addListener(MouseAdapter listener){
        this.addMouseListener(listener);
    }

}
