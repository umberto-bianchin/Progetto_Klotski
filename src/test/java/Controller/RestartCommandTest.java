package Controller;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RestartCommand
 */
public class RestartCommandTest extends KlotskiControllerTest {

    /**
     * Test case for the mousePressed() method of RestartCommand class
     * It verifies the behavior of restarting a game
     * @throws IOException if there is an error in the solver
     * @throws ParseException if there is an error in the solver
     * @throws SQLException if occur a database error
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    public void testMousePressedRestart(int configuration) throws IOException, ParseException, SQLException {

        Rectangle[] positions = startGame(configuration);

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