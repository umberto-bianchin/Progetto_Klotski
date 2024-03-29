package Controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for NextCommand
 */
class NextCommandTest extends KlotskiControllerTest {

    /**
     * @param configuration int between 0-3 to test the Solver
     * @throws SQLException if occur a database error
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void testMousePressedUndoNext(int configuration) throws SQLException {

        Rectangle[] positions = startGame(configuration);

        testedController.mousePressed(event);
        assertFalse(Arrays.equals(positions, model.getCurrentPositions()));

        while(!model.hasWin())
            testedController.mousePressed(event);

        assertEquals("You won!", message);

    }

    @Override
    protected UIController getTestedController() {
        return new NextCommand(model, view);
    }

}