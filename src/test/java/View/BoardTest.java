package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp(){
        board = new Board(0);
    }


    @Test
    void testSetDisplayedCounter() {
        board.setDisplayedCounter(5);
        assertEquals("Moves: 5", board.displayedCounter.getText());
    }

    @Test
    void testMoveSelectedBlock() {
        Block block = new Block();
        block.setBounds(new Rectangle(0, 0, 100, 100));
        board.selectedBlock = block;

        Rectangle newPos = new Rectangle(100, 0, 100, 100);
        board.moveSelectedBlock(newPos, 1);

        assertEquals(newPos, block.getBounds());
        assertEquals("Moves: 1", board.displayedCounter.getText());
        assertNull(board.selectedBlock);
    }

    @Test
    void testHighlightSelected() {
        Block block1 = new Block();
        Block block2 = new Block();

        board.highlightSelected(block1);
        assertNotNull(block1.getBorder());

        board.highlightSelected(block2);
        assertNull(block1.getBorder());
        assertNotNull(block2.getBorder());

    }

    @Test
    void testSelectBlock() {
        Block block1 = new Block();
        block1.setBounds(new Rectangle(0,0,100,100));
        board.blocks[0] = block1;

        board.selectBlock(new Point(25,40));
        assertEquals(block1, board.selectedBlock);

        board.selectBlock(null);
        assertNull(board.selectedBlock);
    }

    @Test
    void testSetPositions() {
        Rectangle[] positions = {new Rectangle(0,0,100,200), new Rectangle(0,200,100,200), new Rectangle(0,400,100,100),
                new Rectangle(100,0,200,200), new Rectangle(100,200,200,100), new Rectangle(100,300,100,100),
                new Rectangle(200,300,100,100), new Rectangle(300,0,100,200), new Rectangle(300,200,100,200),
                new Rectangle(300,400,100,100)};

        board.setPositions(positions);

        for(int i=0; i<10; i++)
            assertEquals(positions[i], board.blocks[i].getBounds());

    }

}