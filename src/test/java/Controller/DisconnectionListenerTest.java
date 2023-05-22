package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DisconnectionListenerTest {

    /**
     * Test case for the mousePressed() method of DisconnectionListener class.
     * It verifies the behavior of pressing a button with disconnection Listener.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testMousePressedDisconnection() throws SQLException {
        //Prepare data test
        KlotskiModel model = new KlotskiModel();
        final String[] errorMessage = new String[1];

        // showMessage method override in KlotskiUI class to set the state variable
        KlotskiUI view = new KlotskiUI() {
            @Override
            public void showMessage(String message, String title, int messageType) {
                errorMessage[0] = message;
            }
        };

        model.initDatabase();
        model.login("JTest", "JTest");
        view.initUser("JTest");

        DisconnectionListener disconnection = new DisconnectionListener(model, view);
        JButton button = new JButton();
        button.setName("logOut");

        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);

        disconnection.mousePressed(event);

        //Try to get the list of saved games without being authenticated
        assertThrows(IllegalAccessException.class, model::getSavedGameList);

        button.setName("delUser");
        model.registration("JTest2", "JTest2");
        view.initUser("JTest2");
        disconnection.mousePressed(event);

        //Try to log in with credentials just deleted
        assertThrows(RuntimeException.class, () -> model.login("JTest2", "JTest2"));

        //Try to delete a user without being authenticated
        disconnection.mousePressed(event);
        assertEquals("You must login", errorMessage[0]);
    }
}