package View;

import javax.swing.*;
import java.awt.*;

/**
 * The Block class represents a custom JLabel used to display and interact with klotski pieces
 */
public class Block extends JLabel {

    /**
     * Sets the appearance of the block at the specified position
     * @param position The Rectangle  representing the position and size of the block
     */
    public void setBlockAppearance(Rectangle position) {
        setImage(position);
        setBounds(position);
    }

    private void setImage(Rectangle position) {
        setIcon(new ImageIcon(getClass().getResource(getImagePath(position))));
    }

    private static String getImagePath(Rectangle position) {
        return "/images/" + position.width + "x" + position.height + ".png";
    }

    /**
     * Sets the border of the block based on the specified condition
     * @param enableBorder A boolean value indicating whether the border should be enabled or disabled
     */
    public void setBorderEnable(boolean enableBorder) {
        if (enableBorder)
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        else
            setBorder(null);
    }

}
