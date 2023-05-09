package Model;

import java.awt.*;

public class Move {

    private final Rectangle initial_pos;
    private final Rectangle final_pos;

    public Move(Rectangle i, Rectangle f){
        initial_pos = new Rectangle(i);
        final_pos = new Rectangle(f);
    }

    public Rectangle getInitialPosition(){return new Rectangle(initial_pos);}
    public Rectangle getFinalPosition(){return new Rectangle(final_pos);}
    public Move invert(){
        return new Move(final_pos, initial_pos);
    }


}
