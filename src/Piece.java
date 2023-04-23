import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Piece {
    Position position;
    JLabel appearance;
    Border border = BorderFactory.createLineBorder(Color.BLUE);



    Piece(Position intial_position, boolean type){

        position = intial_position;
        appearance = new JLabel();
        appearance.setBorder(border);
        appearance.setOpaque(true);


        if (type)
            appearance.setBackground(Color.red);
        else
            appearance.setBackground(Color.black);

        appearance.setVisible(true);
        appearance.setPreferredSize(new Dimension(position.getWidth()*100, position.getHeight()*100));

    }

    public JLabel getAppearance() {
        return appearance;
    }

    public GridBagConstraints getLayout(GridBagConstraints gbc){
        gbc.gridx = position.getX();
        gbc.gridy = position.getY();
        gbc.gridheight = position.getHeight();
        gbc.gridwidth = position.getWidth();

        return gbc;
    }
}
