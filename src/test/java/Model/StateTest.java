package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for State
 */
public class StateTest {

    private State state;
    private static Rectangle[] initialPos;
    private static final Move expectedMove = new Move(new Rectangle(0,400,100,100), new Rectangle(100,400,100,100));
    private static  Rectangle[] expectedPositions;

    /**
     * Set up method executed before each test
     * Creates a new instance of the State class
     */
    @BeforeEach
    public void setUp() throws SQLException {

        Database db = new Database();
        db.login("JTest", "JTest");
        initialPos = db.getInitialPositions(0);
        db.closeConnection();

        expectedPositions = initialPos.clone();
        expectedPositions[8] = expectedMove.getFinalPosition();

        state = new State(initialPos,0);

    }

    /**
     * Test case for the getCurrentPositions() method
     * It verifies the behavior of getting the current pieces positions
     */
    @Test
    public void testGetCurrentPositions() {
        assertArrayEquals(initialPos, state.getCurrentPositions());
    }

    /**
     * Test case for the setSelectedPiece() method
     * It verifies the behavior of setting the selected pieces
     */
    @Test
    public void testSetSelectedPiece() {
        state.setSelectedPiece(null);
        assertNull(state.selectedPiece);

        state.setSelectedPiece(new Point(50, 20));
        assertEquals(initialPos[1], state.selectedPiece.getPosition());
    }

    /**
     * Test case for the moveSelectedPiece() method
     * It verifies the behavior of moving the selected piece
     */
    @Test
    public void testMoveSelectedPiece() {
        //Trying to execute moveSelectedPiece with selectedPiece == null
        assertThrows(RuntimeException.class, ()->state.moveSelectedPiece(null));

        //Prepare data test
        Point point = new Point(20,80);
        Point finalPoint = new Point(0, 350);
        state.setSelectedPiece(point);

        //Trying to move a piece without available moves
        state.setSelectedPiece(point);
        assertThrows(RuntimeException.class, ()->state.moveSelectedPiece(finalPoint));

        //Trying to move a piece in a position that intersects another piece
        point.move(20, 420);
        state.setSelectedPiece(point);
        assertThrows(RuntimeException.class, ()->state.moveSelectedPiece(finalPoint));

        //Moving the piece in a correct position
        finalPoint.move(120, 430);
        Move move = state.moveSelectedPiece(finalPoint);

        assertEquals(expectedMove.getInitialPosition(), move.getInitialPosition());
        assertEquals(expectedMove.getFinalPosition(), move.getFinalPosition());
    }

    /**
     * Test case for the undo() method
     * It verifies the behavior of undoing a move
     */
    @Test
    public void testUndo() {
        //Prepare data test
        state.setSelectedPiece(new Point(0,400));
        state.moveSelectedPiece(new Point(100, 400));
        state.undo();

        assertEquals(0, state.moves.size());
        assertArrayEquals(initialPos, state.getCurrentPositions());
    }

    /**
     * Test case for the makeMove() method
     * It verifies the behavior of making a move
     */
    @Test
    public void testMakeMove() {
        state.makeMove(expectedMove);

        //Asserting that the expectedPositions are equals to the effective positions
        assertArrayEquals(expectedPositions, state.getCurrentPositions());
        //Asserting that is added only one move
        assertEquals(1, state.getMoves().size());
    }

}