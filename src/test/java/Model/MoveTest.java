package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    /**
     * Test case for the reverse() method.
     * It verifies the behavior of reversing the initial and final positions of a move.
     */
    @Test
    void testReverse() {
        //Prepare test data
        Move move = new Move(new Rectangle(0, 0, 100, 100), new Rectangle(100,0,100,100));
        Move reverse = move.reverse();

        assertEquals(move.getInitialPosition(), reverse.getFinalPosition());
        assertEquals(move.getFinalPosition(), reverse.getInitialPosition());
    }
}