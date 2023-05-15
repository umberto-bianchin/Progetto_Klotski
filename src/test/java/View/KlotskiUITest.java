package View;

import Model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;


class KlotskiUITest {

    private KlotskiUI klotskiUI;

    @BeforeEach
    void setUp(){
        klotskiUI = new KlotskiUI();
    }


    @Test
    void testInitStart() {
        klotskiUI.initStart();

        assertEquals("Select a Configuration", ((JLabel) klotskiUI.mainPane.getComponent(0)).getText());
        assertNotNull(klotskiUI.mainPane.getComponent(1));
        assertNotNull(klotskiUI.mainPane.getComponent(2));
    }

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

    @Test
    void testMakeMove() {
        Rectangle[] positions = {new Rectangle(0,0,100,200), new Rectangle(0,200,100,200), new Rectangle(0,400,100,100),
                new Rectangle(100,0,200,200), new Rectangle(100,200,200,100), new Rectangle(100,300,100,100),
                new Rectangle(200,300,100,100), new Rectangle(300,0,100,200), new Rectangle(300,200,100,200),
                new Rectangle(300,400,100,100)};
        klotskiUI.initGame(positions, 0);
        Move move = new Move(positions[2], new Rectangle(100, 400, 100, 100));
        Block block = new Block();
        block.setBounds(move.getFinalPosition());
        klotskiUI.board.selectedBlock = block;
        klotskiUI.board.moveSelectedBlock(move.getInitialPosition(), 1);
        assertEquals(move.getInitialPosition(), block.getBounds());

    }

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