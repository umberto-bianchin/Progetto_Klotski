package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthListenerTest {
    String errorMessage;

    /**
     * Test case for the mousePressed() method of AuthListener class.
     * It verifies the behavior of pressing an authentication button.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testMousePressedAuth() throws SQLException {
        //Prepare data test
        KlotskiModel model = new KlotskiModel();
        model.initDatabase();

        // showMessage method override in KlotskiUI class to set the state variable
        KlotskiUI view = new KlotskiUI() {
            @Override
            public void showMessage(String message, String title, int messageType) {
                errorMessage = message;
            }
        };
        AuthListener auth = new AuthListener(model, view);
        JButton button = new JButton();

        //Try to log in with invalid credentials
        button.putClientProperty("username", "Wrong Username");
        button.putClientProperty("password", "JTest");
        button.setText("Log in");
        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);

        auth.mousePressed(event);
        // Verify that the state variable contains the correct error message
        assertEquals("Invalid username or password", errorMessage);

        //Try to sign up with invalid credentials and with a username already used
        button.putClientProperty("username", "");
        button.setText("Sign up");
        auth.mousePressed(event);
        assertEquals("Can't register players with blank username or password", errorMessage);

        button.putClientProperty("username", "JTest");
        auth.mousePressed(event);
        assertEquals("Can't register another player with the same username", errorMessage);

        //Log in with correct credentials
        button.putClientProperty("username", "JTest");
        button.setText("Log in");
        assertDoesNotThrow(()->auth.mousePressed(event));
    }
}