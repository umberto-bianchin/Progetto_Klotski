package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DisconnectionListener.
 */
public class DisconnectionListenerTest {
    private KlotskiModel model;
    private KlotskiUI view;
    private DisconnectionListener disconnection;
    private JButton button;
    private String[] errorMessage;

    /**
     * Set up method executed before each test.
     * Initializes the necessary variables and sets up the KlotskiModel and KlotskiUI instances.
     * @throws SQLException if there is an error in establishing the database connection.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        model = new KlotskiModel();
        errorMessage = new String[1];

        // showMessage method override in KlotskiUI class to capture error messages
        view = new KlotskiUI() {
            @Override
            public void showMessage(String message, String title, int messageType) {
                errorMessage[0] = message;
            }
        };

        model.initDatabase();
        model.login("JTest", "JTest");
        view.initUser("JTest");

        disconnection = new DisconnectionListener(model, view);
        button = new JButton();
    }

    /**
     * Tear down method executed after each test
     * Closes the database connection
     */
    @AfterEach
    public void tearDown(){
        model.closeDatabaseConnection();
    }

    /**
     * Test case for the mousePressed() method of DisconnectionListener class.
     * It verifies the behavior of pressing a button with DisconnectionListener.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    public void testMousePressedDisconnection() throws SQLException {
        button.setName("logOut");

        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);

        disconnection.mousePressed(event);

        // Verify that attempting to get the list of saved games without being authenticated throws an IllegalAccessException
        assertThrows(IllegalAccessException.class, model::getSavedGameList);

        button.setName("delUser");
        model.registration("JTest2", "JTest2");
        view.initUser("JTest2");
        disconnection.mousePressed(event);

        // Verify that attempting to log in with just deleted credentials throws a RuntimeException
        assertThrows(RuntimeException.class, () -> model.login("JTest2", "JTest2"));

        // Verify that attempting to delete a user without being authenticated sets the correct error message
        disconnection.mousePressed(event);
        assertEquals("You must login", errorMessage[0]);
    }
}