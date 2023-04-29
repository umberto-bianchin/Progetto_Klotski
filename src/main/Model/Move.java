package Model;
import java.awt.Rectangle;

enum Direction {DX, SX, UP, DOWN }

public class Move {

    private final Rectangle rectangle;
    private final Direction direction;

    public Move(Rectangle r, Direction d){
        rectangle = r;
        direction = d;
    }

    public Rectangle getRectangle(){return rectangle;}
    public Direction getDirection(){return direction;}

}
