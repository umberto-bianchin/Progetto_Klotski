package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        piece.move(new Rectangle(100, 0, 100, 100));
        assertEquals(new Rectangle(100, 0, 100, 100), piece.getPosition());
    }

    /**
     * Test case for the checkAvailable() method
     * It verifies the behavior of checking an available move of a piece
     */
    @ParameterizedTest
    @CsvSource({"-20,80,-100,0", "70,-24,0,-100", "40,190,0,100", "190,65,100,0"})
    public void testCheckAvailable(int x, int y, int pos_x, int pos_y) {
        assertEquals(new Rectangle(pos_x, pos_y, 100, 100), piece.checkAvailable(new Point(x, y)));


    }

    /**
     * Test case for the checkAvailable() method
     * Assert that a misleading checkAvailable throws an error
     */
    @Test
    public void testCheckAvailableException() {
        Piece piece2 = new Piece(new Rectangle(0,0,200,200));
        assertThrows(RuntimeException.class, () -> piece2.checkAvailable(new Point(20, 80)));
    }

    /**
     * Test case for the checkAvailable() method
     * It verifies the behavior of checking an available move of a piece in an illegal position
     */
    @ParameterizedTest
    @CsvSource({"60,80", "-300,70", "+900,110"})
    public void testCheckAvailableNull(int x, int y) {
        assertNull(piece.checkAvailable(new Point(x, y)));

    }



}