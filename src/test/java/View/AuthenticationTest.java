package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
    private Authentication auth;

    /**
     * Set up method executed before each test.
     * Creates a new instance of the Authentication class.
     * Creates a new instance of the JFrame class.
     */
    @BeforeEach
    void setUp(){
        JFrame frame = new JFrame();
        auth = new Authentication(frame);
    }

    /**
     * Test case for the initAuthentication() method.
     * It verifies the behavior of initializing the authentication buttons of the UI.
     */
    @Test
    void testInitAuthentication() {
        auth.initAuthentication();
        JButton[] buttons = new JButton[2];
        for (int i = 0; i < 2; i++) {
            buttons[i] = (JButton) auth.getComponent(i);
        }
        assertEquals("Sign up", buttons[0].getClientProperty("name"));
        assertEquals("Log in", buttons[1].getClientProperty("name"));
    }

    /**
     * Test case for the initUser() method.
     * It verifies the behavior of initializing the user.
     */
    @Test
    void testInitUser() {
        auth.initUser("test");
        assertEquals(3, auth.getComponentCount());
        assertTrue(auth.getComponent(0) instanceof JLabel);
        assertEquals("test", ((JLabel)auth.getComponent(0)).getText());
        assertTrue(auth.getComponent(1) instanceof JButton);
        assertTrue(auth.getComponent(2) instanceof JButton);

    }
}