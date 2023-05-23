package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    private Piece piece;

    /**
     * Set up method executed before each test.
     * Creates a new instance of the Piece class with an initial position.
     */
    @BeforeEach
    public void setUp(){
        piece = new Piece(new Rectangle(0,0,100,100));
    }

    /**
     * Test case for the move() method.
     * It verifies the behavior of moving a piece.
     */
    @Test
    public void testMove() {
        Rectangle newPosition = new Rectangle(100, 0, 100, 100);
        piece.move(newPosition);

        assertEquals(newPosition, piece.getPosition());
    }

    /**
     * Test case for the checkAvailable() method.
     * It verifies the behavior of checking an available move of a piece.
     */
    @Test
    public void testCheckAvailable() {
        Point available = new Point(100, 0);
        Rectangle expectedAvailableMove = new Rectangle(100, 0, 100, 100);
        assertEquals(expectedAvailableMove, piece.checkAvailable(available));

        Point notAvailable = new Point(100, 100);
        assertNull(piece.checkAvailable(notAvailable));
    }
}