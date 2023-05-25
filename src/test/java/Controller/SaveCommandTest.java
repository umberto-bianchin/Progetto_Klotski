package Controller;

import View.KlotskiUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SaveCommand
 */
public class SaveCommandTest extends KlotskiControllerTest {

    private static int n = 0;
    private static String correctlySaved;
    private static String notLogged;

    /**
     * Set up method executed before all test
     * Initializes the necessary variables and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeEach
    public void setUp() throws SQLException {
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
    }

    /**
     * Test case for the mousePressed() method of SaveCommand class
     * It verifies the behavior of saving a game
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if the player is not logged in to the system
     */
    @Test
    public void testMousePressedSaveCommand() throws SQLException, IllegalAccessException {

        //Trying to save without being authenticated
        testedController.mousePressed(event);
        assertEquals("Auth dialog displayed correctly", notLogged);

        //Saving the game first with black name, and after with a correct name
        model.login("JTest", "JTest");
        view.initUser("JTest");
        testedController.mousePressed(event);
        assertEquals("You can't save match with blank names", message);
        assertEquals("Successfully saved the game", correctlySaved);
        model.deleteAll();

    }


    @Override
    protected UIController getTestedController() {
        view = new KlotskiUI() {
            @Override
            public String askGameName() {
                if(n == 0)
                    return "";
                return "Test";
            }
            @Override
            public void showMessage(String message, String title, int type) {
                if(n++ == 0)
                    KlotskiControllerTest.message = message;
                else
                    correctlySaved = message;
            }
            @Override
            public boolean showAuthenticationDialog() {
                notLogged = "Auth dialog displayed correctly";
                return false;
            }
        };

        return new SaveCommand(model, view);
    }


}