package Controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NextCommandTest extends KlotskiControllerTest {

    private static Rectangle[] positions;

    /**
     * Initializes the necessary variables and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    private void setUp(int configuration) throws SQLException {

        model.initState(configuration);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        positions = model.getCurrentPositions();

    }

    /**
     * @param configuration int between 0-3 to test the Solver
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void testMousePressedUndoNext(int configuration) throws SQLException {

        setUp(configuration);

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