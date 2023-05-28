package View;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SavedGamesDialog
 */
public class SavedGamesDialogTest {

    /**
     * Test case for the SavedGamesDialog class
     * It verifies the behavior of creating a new SavedGamesDialog instance
     */
    @Test
    public void testSavedGamesDialog(){
        SavedGamesDialog dialog = new SavedGamesDialog(null, null, new Vector<>(Arrays.asList("First game", "Second game")));

        assertEquals(5, dialog.panel.getComponentCount());
        assertEquals("gameFirst game", dialog.panel.getComponent(1).getName());
        assertEquals("gameSecond game", dialog.panel.getComponent(3).getName());
    }

}