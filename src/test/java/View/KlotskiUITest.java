package View;

import Model.Database;
import Model.Move;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for KlotskiUI
 */
public class KlotskiUITest {

    private static KlotskiUI klotskiUI;
    private static Rectangle[] positions;

    /**
     * Set up method executed before all tests
     * Sets up the KlotskiUI instance
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        klotskiUI = new KlotskiUI();

        Database db = new Database();
        positions = db.getInitialPositions(0);
        db.closeConnection();
    }

    /**
     * Test case for the initStart() method
     * It verifies the behavior of initializing the choose configuration screen of the UI
     */
    @Test
    public void testInitStart() {
        klotskiUI.initStart();

        assertEquals("Select a Configuration", ((JLabel) klotskiUI.mainPane.getComponent(0)).getText());

        //Asserting that the mainPane components are not null
        assertNotNull(klotskiUI.mainPane.getComponent(1));
        assertNotNull(klotskiUI.mainPane.getComponent(2));
    }

    /**
     * Test case for the initGame() method
     * It verifies the behavior of initializing the game screen of the UI
     */
    @Test
    public void testInitGame() {
        klotskiUI.initGame(positions, 0);

        assertNotNull(klotskiUI.buttons);
        assertNotNull(klotskiUI.board);
    }

    /**
     * Test case for the restart() method
     * It verifies the behavior of restarting a game
     */
    @Test
    public void testRestart() {
        klotskiUI.initGame(positions, 3);
        klotskiUI.board.selectedBlock = new Block();
        klotskiUI.restart(positions);

        assertNull(klotskiUI.board.selectedBlock);
        assertEquals("Moves: 0", klotskiUI.board.displayedCounter.getText());
    }

    /**
     * Test case for the makeMove() method
     * It verifies the behavior of making a move
     */
    @Test
    public void testMakeMove() {
        klotskiUI.initGame(positions, 0);
        Move move = new Move(positions[2], new Rectangle(100, 400, 100, 100));
        klotskiUI.makeMove(move, 1);
        assertEquals(move.getFinalPosition(), klotskiUI.board.blocks[2].getBounds());
    }

}