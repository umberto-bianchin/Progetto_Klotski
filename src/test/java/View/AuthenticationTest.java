package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Authentication
 */
public class AuthenticationTest {
    private Authentication auth;

    /**
     * Set up method executed before each test
     * Sets up the Authentication instance and creates a new instance of the JFrame class
     */
    @BeforeEach
    public void setUp(){
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
    @Test
    public void testInitUser() {
        auth.initUser("test");

        assertEquals(4, auth.getComponentCount());
        assertTrue(auth.getComponent(0) instanceof JLabel);
        assertEquals("test", ((JLabel)auth.getComponent(0)).getText());
        assertTrue(auth.getComponent(1) instanceof JButton);
        assertTrue(auth.getComponent(2) instanceof JButton);
        assertTrue(auth.getComponent(3) instanceof JButton);
    }

}