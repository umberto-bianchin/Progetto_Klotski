package View;

import Model.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Board
 */
public class BoardTest {

    private Board board;

    /**
     * Set up method executed before each test
     * Sets up the Board instance
     */
    @BeforeEach
    public void setUp(){
        board = new Board();
    }

    /**
     * Test case for the setDisplayedCounter() method
     * It verifies the behavior of setting the moves counter
     */
    @Test
    public void testSetDisplayedCounter() {
        board.setDisplayedCounter(5);
        assertEquals("Moves: 5", board.displayedCounter.getText());
    }

    /**
     * Test case for the moveSelectedBlock() method
     * It verifies the behavior of moving a block
     */
    @Test
    public void testMoveSelectedBlock() {
        Block block = new Block();
        block.setBounds(new Rectangle(0, 0, 100, 100));
        board.selectedBlock = block;

        board.moveSelectedBlock(new Rectangle(100, 0, 100, 100), 1);

        assertEquals(new Rectangle(100, 0, 100, 100), block.getBounds());
        assertEquals("Moves: 1", board.displayedCounter.getText());
        assertNull(board.selectedBlock);
    }

    /**
     * Test case for the highlightSelected() method
     * It verifies the behavior of highlighting a block
     */
    @Test
    public void testHighlightSelected() {
        Block block1 = new Block();
        Block block2 = new Block();

        board.highlightSelected(block1);
        assertNotNull(block1.getBorder());

        board.highlightSelected(block2);
        assertNull(block1.getBorder());
        assertNotNull(block2.getBorder());
    }

    /**
     * Test case for the selectBlock() method
     * It verifies the behavior of selecting a block
     */
    @Test
    public void testSelectBlock() {
        board.blocks[0].setBounds(new Rectangle(0,0,100,100));

        //Selecting a block simulating a click on the screen
        board.selectBlock(new Point(25,40));
        assertEquals(board.blocks[0], board.selectedBlock);

        board.selectBlock(null);
        assertNull(board.selectedBlock);
    }

    /**
     * Test case for the setPositions() method
     * It verifies the behavior of setting the positions of the board blocks
     */
    @Test
    public void testSetPositions() throws SQLException {
        //Prepare data test
        Database db = new Database();
        Rectangle[] initialPos = db.getInitialPositions(0);
        board.setPositions(initialPos);
        db.closeConnection();

        for(int i=0; i<10; i++)
            assertEquals(initialPos[i], board.blocks[i].getBounds());
    }

}