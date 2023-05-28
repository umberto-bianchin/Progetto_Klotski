package View;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Authentication
 */
public class AuthenticationTest {
    private static Authentication auth;

    /**
     * Set up method executed before all tests
     * Sets up the Authentication instance and creates a new instance of the JFrame class
     */
    @BeforeAll
    public static void setUp(){
        JFrame frame = new JFrame();
        auth = new Authentication(frame);
    }

    /**
     * Test case for the initAuthentication() method
     * It verifies the behavior of initializing the authentication buttons of the UI
     */
    @Test
    public void testInitAuthentication() {
        auth.initAuthentication();

        assertEquals("Sign up", ((JButton) auth.getComponent(0)).getClientProperty("name"));
        assertEquals("Log in", ((JButton) auth.getComponent(1)).getClientProperty("name"));
    }

    /**
     * Test case for the initUser() method
     * It verifies the behavior of initializing the user
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void testInitUser(int number) {
        auth.initUser("test");

        assertEquals(4, auth.getComponentCount());
        assertEquals("test", ((JLabel)auth.getComponent(0)).getText());
        assertTrue(auth.getComponent(number) instanceof JButton);
    }

}