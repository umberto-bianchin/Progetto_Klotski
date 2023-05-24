package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Block
 */
public class BlockTest {

    private Block block;

    /**
     * Set up method executed before each test
     * Sets up the Block instance
     */
    @BeforeEach
    public void setUp(){
        block = new Block();
    }

    /**
     * Test case for the setBlockAppearance() method
     * It verifies the behavior of setting the appearance of a block
     */
    @Test
    public void testSetBlockAppearance() {
        //Prepare data test
        Rectangle position = new Rectangle(100,100,200,200);

        block.setBlockAppearance(position);

        assertEquals(position, block.getBounds());
    }

    /**
     * Test case for the setBorder() method
     * It verifies the behavior of setting the border of a block
     */
    @Test
    public void testSetBorder() {
        block.setBorderEnable(true);
        assertNotNull(block.getBorder());

        block.setBorderEnable(false);
        assertNull(block.getBorder());
    }

}