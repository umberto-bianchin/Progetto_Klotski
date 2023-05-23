package View;

import org.junit.jupiter.api.Test;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SavedGamesDialogTest {
    private final Vector<String> games = new Vector<>();

    @Test
    void testSavedGamesDialog(){
        games.add("First game");
        games.add("Second game");
        SavedGamesDialog dialog = new SavedGamesDialog(null, null, games);

        assertEquals(5, dialog.panel.getComponentCount());
        assertEquals("gameFirst game", dialog.panel.getComponent(1).getName());
        assertEquals("gameSecond game", dialog.panel.getComponent(3).getName());
    }



}