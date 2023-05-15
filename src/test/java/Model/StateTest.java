package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    private State state;
    Rectangle[] initialPos = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
            new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
            new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
            new Rectangle(300,400,100,100)};

    /**
     * Set up method executed before each test.
     * Creates a new instance of the State class.
     */
    @BeforeEach
    void setUp() {
        state = new State(initialPos,0);
    }

    /**
     * Test case for the getCurrentPositions() method.
     * It verifies the behavior of getting the current pieces positions.
     */
    @Test
    void testGetCurrentPositions() {
        Rectangle[] positions = state.getCurrentPositions();

        for(int i=0; i<10; i++)
            assertEquals(initialPos[i], positions[i]);
    }

    /**
     * Test case for the setSelectedPiece() method.
     * It verifies the behavior of setting the selected pieces.
     */
    @Test
    void testSetSelectedPiece() {
        Point point = null;

        state.setSelectedPiece(point);
        assertNull(state.selectedPiece);

        point = new Point(50, 20);
        state.setSelectedPiece(point);

        assertEquals(initialPos[1], state.selectedPiece.getPosition());
    }

    /**
     * Test case for the moveSelectedPiece() method.
     * It verifies the behavior of moving the selected piece.
     */
    @Test
    void testMoveSelectedPiece() {
        state.setSelectedPiece(null);

        //Try to execute moveSelectedPiece with the selectedPiece == null
        assertThrows(RuntimeException.class, ()->state.moveSelectedPiece(null));

        //Prepare test data
        Point point = new Point(20,80);
        Point finalPoint = new Point(0, 350);
        state.setSelectedPiece(point);

        //Try to move a piece without available moves
        state.setSelectedPiece(point);
        assertThrows(RuntimeException.class, ()->state.moveSelectedPiece(finalPoint));

        //Try to move a piece in a position that intersects another piece
        point.move(20, 420);
        state.setSelectedPiece(point);
        assertThrows(RuntimeException.class, ()->state.moveSelectedPiece(finalPoint));

        //Move the piece in a correct position
        finalPoint.move(120, 430);
        Move expectedMove= new Move(new Rectangle(0,400,100,100), new Rectangle(100,400,100,100));
        Move move = state.moveSelectedPiece(finalPoint);

        assertEquals(expectedMove.getInitialPosition(), move.getInitialPosition());
        assertEquals(expectedMove.getFinalPosition(), move.getFinalPosition());
    }

    /**
     * Test case for the undo() method.
     * It verifies the behavior of undoing a move.
     */
    @Test
    void testUndo() {
        //Prepare test data
        state.setSelectedPiece(new Point(0,400));
        state.moveSelectedPiece(new Point(100, 400));

        Rectangle[] positions = state.getCurrentPositions();

        assertNotEquals(Arrays.hashCode(initialPos), Arrays.hashCode(positions));

        state.undo();
        positions = state.getCurrentPositions();

        assertEquals(0, state.moves.size());
        assertEquals(Arrays.hashCode(initialPos), Arrays.hashCode(positions));

    }

    /**
     * Test case for the makeMove() method.
     * It verifies the behavior of making a move.
     */
    @Test
    void testMakeMove() {
        //Prepare test data
        Move move= new Move(new Rectangle(0,400,100,100), new Rectangle(100,400,100,100));
        Rectangle[] expectedPositions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(100,400,100,100),
                new Rectangle(300,400,100,100)};

        state.makeMove(move);

        // Check if the expectedPositions are equals to the effective positions
        assertEquals(Arrays.hashCode(expectedPositions), Arrays.hashCode(state.getCurrentPositions()));
        // Check that is added only one move
        assertEquals(1, state.getMoves().size());

    }
}