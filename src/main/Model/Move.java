package Model;
import java.awt.Rectangle;

enum Direction {DX, SX, UP, DOWN }

public class Move {

    private final Rectangle block;
    private final Direction direction;

    public Move(Rectangle b, Direction d){
        block = b;
        direction = d;
    }

    public Rectangle getBlock(){return block;}
    public Direction getDirection(){return direction;}

}
