package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AuthListener
 */
public class AuthListenerTest {
    private static KlotskiModel model;
    private static AuthListener auth;
    private static JButton button;
    private static MouseEvent event;
    private static String errorMessage;

    /**
     * Set up method executed before each test
     * Initializes the variable needed for the tests and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void initAll() throws SQLException {
        model = new KlotskiModel();
        model.initDatabase();

        // showMessage method override in KlotskiUI class to capture error messages
        KlotskiUI view = new KlotskiUI() {
            @Override
            public void showMessage(String message, String title, int messageType) {
                errorMessage = message;
            }
        };
        auth = new AuthListener(model, view);
        button = new JButton();
        button.putClientProperty("password", "JTest");
        event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);
    }

    /**
     * Closes the database connection after the AuthListenerTest
     */
    @AfterAll
    public static void tearDownAll(){
        model.closeDatabaseConnection();
    }

    /**
     * Nested class for tests related to mousePressed() method in AuthListener
     */
    @Nested
    class MousePressedAuthTests {
        /**
         * Test case for the mousePressed() method with invalid login credentials
         * It verifies the behavior of pressing the login button with invalid credentials
         */
        @Test
        public void testInvalidLoginCredentials(){
            button.putClientProperty("username", "Wrong Username");
            button.setText("Log in");

            auth.mousePressed(event);
            // Verify that the state variable contains the correct error message
            assertEquals("Invalid username or password", errorMessage);
        }

        /**
         * Test case for the mousePressed() method with invalid signup credentials
         * It verifies the behavior of pressing the signup button with invalid credentials
         */
        @Test
        public void testInvalidSignUpCredentials(){
            button.putClientProperty("username", "");
            button.setText("Sign up");

            auth.mousePressed(event);

            assertEquals("Can't register players with blank username or password", errorMessage);

            button.putClientProperty("username", "JTest");
            auth.mousePressed(event);

            assertEquals("Can't register another player with the same username", errorMessage);
        }

        /**
         * Test case for the mousePressed() method with valid login credentials
         * It verifies the behavior of pressing the login button with valid credentials
         */
        @Test
        public void testValidLoginCredentials() {
            button.putClientProperty("username", "JTest");
            button.setText("Log in");

            assertDoesNotThrow(() -> auth.mousePressed(event));
        }
    }
}