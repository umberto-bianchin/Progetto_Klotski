package Controller;

import View.KlotskiUI;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for SavedGamesListener
 */
class SavedGamesListenerTest extends KlotskiControllerTest{

    private static SaveCommand save;

    @BeforeEach
    public void init()  throws SQLException, IllegalAccessException {
        model.login("JTest", "JTest");
        model.deleteAll();
    }


    @Nested
    class mousePressedSavedGameTest{

        /**
         * It verifies that the configuration of the game is properly restored after resuming the game
         * @throws SQLException if a database access error occurs
         */
        @Test
        void resumeGameTest() throws SQLException, IOException, ParseException {

            createSavedGame();
            Rectangle[] configuration = model.getCurrentPositions();

            model.nextBestMove();
            pressButton("gameTest");
            assertArrayEquals(configuration, model.getCurrentPositions());

        }

        /**
         * It verifies that resuming or deleting a non-valid game name result in an error
         * @param buttonName the name of the button triggering the test and the relative game name
         *
         */
        @ParameterizedTest
        @ValueSource(strings = {"gameTestWrong", "deleteTestWrong"})
        void exceptionTest(String buttonName) {

            pressButton(buttonName);
            assertEquals(message, "Illegal operation on empty result set.");

        }

        /**
         * It verifies that a saved game is successfully deleted
         * @param buttonName the name of the button triggering the test and the relative game name
         * @throws SQLException if a database access error occurs
         */
        @ParameterizedTest
        @ValueSource(strings = {"deleteTest", "delAll"})
        void deleteTest(String buttonName) throws SQLException, IllegalAccessException {

            createSavedGame();
            pressButton(buttonName);
            assertEquals(model.getSavedGameList().size(), 0);

        }
    }

    /**
     * Creates a saved game for testing purposes
     * @throws SQLException if a database access error occurs
     */
    private void createSavedGame() throws SQLException {
        startGame(0);
        save.mousePressed(event);
    }

    /**
     * Emulate the pressing of the button associated with the SavedGameListener, with the specified name
     */
    private void pressButton(String buttonName){
        button.setName(buttonName);
        testedController.mousePressed(event);
    }


    @Override
    protected UIController getTestedController() {

        view = new KlotskiUI() {
            @Override
            public String askGameName() {
                return "Test";
            }
            @Override
            public void showMessage(String message, String title, int type) {
                KlotskiControllerTest.message = message;
            }
            @Override
            public void showSavedGames(Vector<String> savedGamesNames, MouseAdapter listener) {}
        };

        save = new SaveCommand(model, view);
        return new SavedGamesListener(model, view, save);
    }

}