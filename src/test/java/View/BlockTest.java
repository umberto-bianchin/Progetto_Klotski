package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    private Block block;

    /**
     * Set up method executed before each test.
     * Creates a new instance of the Block class.
     */
    @BeforeEach
    void setUp(){
        block = new Block();
    }

    /**
     * Test case for the setBlockAppearance() method.
     * It verifies the behavior of setting the appearance of a block.
     */
    @Test
    void testSetBlockAppearance() {
        Rectangle position = new Rectangle(100,100,200,200);
        block.setBlockAppearance(position);

        assertEquals(position, block.getBounds());
    }

    /**
     * Test case for the setBorder() method.
     * It verifies the behavior of setting the border of a block.
     */
    @Test
    void testSetBorder() {
        block.setBorder(true);
        assertNotNull(block.getBorder());
        block.setBorder(false);
        assertNull(block.getBorder());
    }
}