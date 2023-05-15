package View;

import Model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class KlotskiUITest {

    private KlotskiUI klotskiUI;

    /**
     * Set up method executed before each test.
     * Creates a new instance of the KlotskiUI class.
     */
    @BeforeEach
    void setUp(){
        klotskiUI = new KlotskiUI();
    }

    /**
     * Test case for the initStart() method.
     * It verifies the behavior of initializing the choose configuration screen of the UI.
     */
    @Test
    void testInitStart() {
        klotskiUI.initStart();

        assertEquals("Select a Configuration", ((JLabel) klotskiUI.mainPane.getComponent(0)).getText());
        assertNotNull(klotskiUI.mainPane.getComponent(1));
        assertNotNull(klotskiUI.mainPane.getComponent(2));
    }

    /**
     * Test case for the initGame() method.
     * It verifies the behavior of initializing the game screen of the UI.
     */
    @Test
    void testInitGame() {
        Rectangle[] positions = {new Rectangle(0,0,100,200), new Rectangle(0,200,100,200), new Rectangle(0,400,100,100),
                new Rectangle(100,0,200,200), new Rectangle(100,200,200,100), new Rectangle(100,300,100,100),
                new Rectangle(200,300,100,100), new Rectangle(300,0,100,200), new Rectangle(300,200,100,200),
                new Rectangle(300,400,100,100)};
        klotskiUI.initGame(positions, 0);
        assertNotNull(klotskiUI.buttons);
        assertNotNull(klotskiUI.board);
    }

    /**
     * Test case for the restart() method.
     * It verifies the behavior of restarting a game.
     */
    @Test
    void testRestart() {
        Rectangle[] positions = {new Rectangle(0,0,100,200), new Rectangle(0,200,100,200), new Rectangle(0,400,100,100),
                new Rectangle(100,0,200,200), new Rectangle(100,200,200,100), new Rectangle(100,300,100,100),
                new Rectangle(200,300,100,100), new Rectangle(300,0,100,200), new Rectangle(300,200,100,200),
                new Rectangle(300,400,100,100)};
        klotskiUI.initGame(positions, 3);
        klotskiUI.board.selectedBlock = new Block();
        klotskiUI.restart(positions);
        assertNull(klotskiUI.board.selectedBlock);
        assertEquals("Moves: 0", klotskiUI.board.displayedCounter.getText());
    }

    /**
     * Test case for the makeMove() method.
     * It verifies the behavior of making a move.
     */
    @Test
    void testMakeMove() {
        Rectangle[] positions = {new Rectangle(0,0,100,200), new Rectangle(0,200,100,200), new Rectangle(0,400,100,100),
                new Rectangle(100,0,200,200), new Rectangle(100,200,200,100), new Rectangle(100,300,100,100),
                new Rectangle(200,300,100,100), new Rectangle(300,0,100,200), new Rectangle(300,200,100,200),
                new Rectangle(300,400,100,100)};
        klotskiUI.initGame(positions, 0);
        Move move = new Move(positions[2], new Rectangle(100, 400, 100, 100));
        klotskiUI.makeMove(move, 1);
        Block block = klotskiUI.board.blocks[2];
        assertEquals(move.getFinalPosition(), block.getBounds());
    }

    /**
     * Test case for the askGameName() method.
     * It verifies the behavior of asking the game name.
     */
    @Test
    void testAskGameName() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        String expectedInput = "test";
        final String[] input = {""};

        Thread t = new Thread(() -> input[0] = klotskiUI.askGameName());
        t.start();
        robot.delay(1000);
        for (char c : expectedInput.toCharArray()) {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
            robot.delay(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        t.join();

        assertEquals(expectedInput, input[0]);
    }
}