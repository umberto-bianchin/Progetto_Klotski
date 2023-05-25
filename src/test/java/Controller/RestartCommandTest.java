package Controller;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RestartCommand
 */
public class RestartCommandTest extends KlotskiControllerTest {

    private static Rectangle[] positions;

    /**
     * Set up method executed before all tests
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
     * Test case for the mousePressed() method of RestartCommand class
     * It verifies the behavior of restarting a game
     * @throws IOException if there is an error in the solver
     * @throws ParseException if there is an error in the solver
     */
    @Test
    public void testMousePressedRestart() throws IOException, ParseException {
        //Make two moves to test the restart button
        view.makeMove(model.nextBestMove(), model.getCounter());
        view.makeMove(model.nextBestMove(), model.getCounter());

        //Assert the counter is zero after restarting a game
        testedController.mousePressed(event);
        assertEquals(0, model.getCounter());

        //Assert the current configuration is equal to the initial configuration
        assertArrayEquals(model.getCurrentPositions(), positions);

    }

    @Override
    protected UIController getTestedController() {
        return new RestartCommand(model, view);
    }

}