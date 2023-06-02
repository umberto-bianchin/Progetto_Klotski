package Model;

import java.awt.*;

/**
 * The class represents a game piece with a position and its available moves
 * It provides methods to manipulate and query the piece's state
 */
class Piece {
    private final Rectangle position;
    private final Rectangle[] availableMoves = new Rectangle[4];
    private static final Rectangle FINISH_POSITION = new Rectangle(100, 300, 200, 200);

    /**
     * @param position the initial position of the piece
     */
    public Piece(Rectangle position) {

        if(position == null)
            throw new NullPointerException("null piece position");

        this.position = new Rectangle(position);
        for (int i = 0; i < 4; i++)
            availableMoves[i] = new Rectangle();
    }

    /**
     * Returns a copy of the current position of the piece
     */
    public Rectangle getPosition() {
        return new Rectangle(position);
    }

    /**
     * Update the available positions of the piece, that are the four translated from all the direction from the initial position
     */
    private void updateAvailable() {
        for (int i = 0; i < 4; i++) {
            availableMoves[i].setBounds(position);
        }

        availableMoves[0].translate(-100, 0);
        availableMoves[1].translate(+100, 0);
        availableMoves[2].translate(0, +100);
        availableMoves[3].translate(0, -100);

    }

    /**
     * Moves the piece to the specified new position.
     * @param newPos the new position of the piece as a {@code Rectangle} object
     * @return true if the new position is at the escape point
     */
    public boolean move(Rectangle newPos) {
        position.setBounds(newPos);
        return position.contains(FINISH_POSITION);
    }

    /**
     * Checks if the piece's current position intersects with the specified position.
     * @return true if there is an intersection, false otherwise
     */
    public boolean intersection(Rectangle newPos) {
        return position.intersects(newPos);
    }

    /**
     * Checks if the specified point is within any of the available move positions.
     * @param p the point to check
     * @return the Rectangle corresponding to the available move that contains the point, or null if no available move contains the point
     * @throws RuntimeException when there are more than one available position associated with that point
     */
    public Rectangle checkAvailable(Point p) {

        updateAvailable();

        int count = 0;
        Rectangle final_position = null;

        for (Rectangle available : availableMoves) {
            if (available.contains(p)) {
                count++;
                final_position = available;
            }
        }

        if(count <= 1)
            return final_position; //null or the final position

        throw new RuntimeException("More than one possible position");

    }

    /**
     * Checks if the specified point is within the piece's current position.
     * @return true if the point is within the piece's current position, false otherwise
     */
    public boolean contains(Point p) {
        return position.contains(p);
    }
}
