package Model;
import java.awt.Rectangle;

public class Move {

    private final Rectangle initial_pos;
    private final Rectangle final_pos;

    public Move(Rectangle i, Rectangle f){
        initial_pos = new Rectangle(i);
        final_pos = new Rectangle(f);

    }

    public Rectangle getInitialPosition(){return initial_pos;}
    public Rectangle getFinalPosition(){return final_pos;}

}
