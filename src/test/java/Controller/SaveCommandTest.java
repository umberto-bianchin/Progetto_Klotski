package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SaveCommand
 */
public class SaveCommandTest {

    private static KlotskiModel model;
    private static KlotskiUI view;
    private static int n = 0;
    private static String correctlySaved;
    private static String notLogged;
    private static String blankName;

    /**
     * Set up method executed before all test
     * Initializes the necessary variables and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        //Prepare data for the test
        model = new KlotskiModel();
        model.initDatabase();

        // Override askGameName, showMessage, and showAuthenticationDialog methods in KlotskiUI class to capture error messages
        view = new KlotskiUI() {
            @Override
            public String askGameName() {
                if(n == 0)
                    return "";
                return "Test";
            }
            @Override
            public void showMessage(String message, String title, int type) {
                if(n == 0){
                    n++;
                    blankName = message;
                }
                else
                    correctlySaved = message;
            }
            @Override
            public boolean showAuthenticationDialog() {
                notLogged = "Dialog displayed correctly";
                return false;
            }
        };

        model.login("JTest", "JTest");
        view.initUser("JTest");
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
    }

    /**
     * Tear down method executed after all tests
     * Closes the database connection
     */
    @AfterAll
    public static void tearDown(){
        model.closeDatabaseConnection();
    }

    /**
     * Test case for the mousePressed() method of SaveCommand class
     * It verifies the behavior of saving a game
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if the player is not logged in to the system
     */
    @Test
    public void testMousePressedSaveCommand() throws SQLException, IllegalAccessException {
        //Saving the game correctly
        SaveCommand save = new SaveCommand(model, view);
        JButton button = new JButton();
        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);
        save.mousePressed(event);
        assertEquals("You can't save match with blank names", blankName);
        assertEquals("Successfully saved the game", correctlySaved);
        model.deleteAll();

        //Trying to save without being authenticated
        model.logout();
        view.logout();
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        save.mousePressed(event);
        assertEquals("Dialog displayed correctly", notLogged);
    }

}