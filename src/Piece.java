import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

//no hardcoded variables!!!

public class Piece {
    Position position;
    JPanel appearance;
    Border border = BorderFactory.createLineBorder(Color.BLUE);

    Piece(Position intial_position, boolean type){

        position = intial_position;
        appearance = new JPanel();

        appearance.setPreferredSize(new Dimension(position.getWidth()*100, position.getHeight()*100));
        appearance.setBorder(border);
        appearance.setOpaque(true);
        appearance.setVisible(true);

        //si setta il pezzo rosso
        if (type)
            appearance.setBackground(Color.red);
        else
            appearance.setBackground(Color.black);
    }

    public JPanel getAppearance() {
        return appearance;
    }

    //si settano le posizioni dei vari pezzi
    public GridBagConstraints getLayout(GridBagConstraints gbc){
        gbc.gridx = position.getX();
        gbc.gridy = position.getY();
        gbc.gridheight = position.getHeight();
        gbc.gridwidth = position.getWidth();

        return gbc;
    }
}
