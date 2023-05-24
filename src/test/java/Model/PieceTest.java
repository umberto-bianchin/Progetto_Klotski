package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Piece
 */
class PieceTest {

    private Piece piece;

    /**
     * Set up method executed before each test
     * Creates a new instance of the Piece class with an initial position
     */
    @BeforeEach
    public void setUp(){
        piece = new Piece(new Rectangle(0,0,100,100));
    }

    /**
     * Test case for the move() method
     * It verifies the behavior of moving a piece
     */
    @Test
    public void testMove() {
        Rectangle newPosition = new Rectangle(100, 0, 100, 100);
        piece.move(newPosition);

        assertEquals(newPosition, piece.getPosition());
    }

    /**
     * Test case for the checkAvailable() method
     * It verifies the behavior of checking an available move of a piece
     */
    @Test
    public void testCheckAvailable() {
        assertEquals(new Rectangle(100, 0, 100, 100), piece.checkAvailable(new Point(100, 0)));
        assertNull(piece.checkAvailable(new Point(100, 100)));
    }

}