package Model;

import java.awt.*;

/**
 * The Move class provides methods to store and manipulate game moves
 */
public class Move {

    private final Rectangle initial_pos;
    private final Rectangle final_pos;

    /**
     *
     @param initialPosition the initial position of the move
     @param finalPosition the final position of the move
     */
    public Move(Rectangle initialPosition, Rectangle finalPosition){
        initial_pos = new Rectangle(initialPosition);
        final_pos = new Rectangle(finalPosition);
    }

    public Rectangle getInitialPosition(){return new Rectangle(initial_pos);}
    public Rectangle getFinalPosition(){return new Rectangle(final_pos);}

    /**
     * @return A new move with reversed final and initial position
     */
    public Move reverse(){
        return new Move(final_pos, initial_pos);
    }


}
