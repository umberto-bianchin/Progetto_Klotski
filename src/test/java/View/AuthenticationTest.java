package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
    private JFrame frame;
    private Authentication auth;

    @BeforeEach
    void setUp(){
        frame = new JFrame();
        auth = new Authentication(frame);
    }

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