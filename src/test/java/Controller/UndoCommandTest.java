package Controller;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UndoCommand
 */
public class UndoCommandTest extends KlotskiControllerTest {

    private static Rectangle[] positions;

    /**
     * Set up method executed before all test
     * Initializes the necessary variables and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void setUp() throws SQLException {

        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        positions = model.getCurrentPositions();

    }

    /**
     * Test case for the mousePressed() method of UndoCommand class
     * It verifies the behavior of undoing a move
     * @throws IOException if there is an error in the solver
     * @throws ParseException if there is an error in the solver
     */
    @Test
    public void testMousePressedUndo() throws IOException, ParseException {
        //Make two moves to test the undo button
        view.makeMove(model.nextBestMove(), model.getCounter());
        view.makeMove(model.nextBestMove(), model.getCounter());

        //Assert that the counter is decreased by one
        testedController.mousePressed(event);
        assertEquals(1, model.getCounter());

        //Assert that the counter is decreased by one
        testedController.mousePressed(event);
        assertEquals(0, model.getCounter());

        //Assert the current configuration is equal to the initial configuration (after two undo)
        assertArrayEquals(model.getCurrentPositions(), positions);

    }

    @Override
    protected UIController getTestedController() {

        assertThrows(NullPointerException.class, () -> new UndoCommand(null, null));
        return new UndoCommand(model, view);
    }

}