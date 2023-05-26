package Controller;

import View.KlotskiUI;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
     * Test case for the mousePressed() method of SaveCommand class
     * It verifies the behavior of saving a game
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if the player is not logged in to the system
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    public void testMousePressedSaveCommand(int configuration) throws SQLException, IllegalAccessException {

        startGame(configuration);

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