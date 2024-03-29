package Controller;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UndoCommand
 */
public class UndoCommandTest extends KlotskiControllerTest {


    /**
     * Test case for the mousePressed() method of UndoCommand class
     * It verifies the behavior of undoing a move
     * @throws IOException if there is an error in the solver
     * @throws ParseException if there is an error in the solver
     * @throws SQLException if occur a database error
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    public void testMousePressedUndo(int configuration) throws IOException, ParseException, SQLException {

        Rectangle[] positions = startGame(configuration);

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