package Model;

import java.awt.*;

public class Piece {

    private Rectangle position;
    private final Rectangle[] availableMoves = new Rectangle[4];
    private static final Rectangle FINISH_POSITION = new Rectangle(100, 300, 200, 200);

    public Piece(Rectangle position){
        this.position = position;
        updateAvailable();
    }

    public Rectangle getPosition(){
        return position;
    }


    public void updateAvailable() {

        for (int i = 0; i < 4; i++)
            availableMoves[i] = new Rectangle(position);

        availableMoves[0].translate(-100, 0);
        availableMoves[1].translate(+100, 0);
        availableMoves[2].translate(0, +100);
        availableMoves[3].translate(0, -100);

    }

    public boolean move(Rectangle newPos) {
        position = newPos;
        updateAvailable();

        return position.contains(FINISH_POSITION);
    }

    public boolean intersection(Rectangle newPos) {
        return position.intersects(newPos);
    }

    public Rectangle checkAvailable(Point p) {

        for (Rectangle available : availableMoves) {
            if (available.contains(p)) {
                return available;
            }
        }
        return null;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }
}
