package Controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DisconnectionListener
 */
public class DisconnectionListenerTest extends KlotskiControllerTest {


    /**
     * Test case for the mousePressed() method of DisconnectionListener class
     * It verifies the behavior of pressing a button with DisconnectionListener
     */
    @Nested
    class MousePressedAuthTests {

        @Test
        public void testNotLoggedDisconnection() {
            button.setName("logOut");
            testedController.mousePressed(event);
            assertEquals("You must login", message);

            // Verify that attempting to get the list of saved games without being authenticated throws an IllegalAccessException
            assertThrows(IllegalAccessException.class, model::getSavedGameList);
        }

        @Test
        public void testDeleteUser() throws SQLException {
            model.registration("JTest2", "JTest2");
            view.initUser("JTest2");
            button.setName("delUser");
            testedController.mousePressed(event);

            // Verify that attempting to log in with just deleted credentials throws a RuntimeException
            assertThrows(RuntimeException.class, () -> model.login("JTest2", "JTest2"));
        }

        @Test
        public void testNotLoggedDeleteUser(){
            button.setName("delUser");
            // Verify that attempting to delete a user without being authenticated sets the correct error message
            testedController.mousePressed(event);
            assertEquals("You must login", message);
        }
    }

    @Override
    protected UIController getTestedController() {
        return new DisconnectionListener(model, view);
    }
}