package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    private Block block;

    @BeforeEach
    void setUp(){
        block = new Block();
    }

    @Test
    void testSetBlockAppearance() {
        Rectangle position = new Rectangle(100,100,200,200);
        block.setBlockAppearance(position);

        assertEquals(position, block.getBounds());
    }

    @Test
    void testSetBorder() {
        block.setBorder(true);
        assertNotNull(block.getBorder());
        block.setBorder(false);
        assertNull(block.getBorder());
    }
}