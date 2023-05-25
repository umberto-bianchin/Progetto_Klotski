package Controller;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AuthListener
 */
public class AuthListenerTest extends KlotskiControllerTest {

    /**
     * Set up initAuthListener, putting the required password
     */
    @BeforeAll
    public static void initAuthListener() {
        button.putClientProperty("password", "JTest");
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

            buttonPress("Wrong Username", "Log in");
            // Verify that the state variable contains the correct error message
            assertEquals("Invalid username or password", message);
        }

        /**
         * Test case for the mousePressed() method with invalid signup credentials
         * It verifies the behavior of pressing the signup button with invalid credentials
         */
        @Test
        public void testInvalidSignUpCredentials(){

            buttonPress("", "Sign up");
            assertEquals("Can't register players with blank username or password", message);

            buttonPress("JTest", "Sign up");
            assertEquals("Can't register another player with the same username", message);
        }

        /**
         * Test case for the mousePressed() method with valid login credentials
         * It verifies the behavior of pressing the login button with valid credentials
         */
        @Test
        public void testValidLoginCredentials() {
            buttonPress("JTest", "Log in");
            assertDoesNotThrow(() -> testedController.mousePressed(event));
        }

        /**
         * Set Up the button with the following text and username property
         * @param type authentication type access such as "Sign up" or "Log in"
         */
        private void buttonPress(String username, String type){
            button.putClientProperty("username", username);
            button.setText(type);
            testedController.mousePressed(event);

        }
    }


    @Override
    protected UIController getTestedController() {
        return new AuthListener(model, view);
    }

}