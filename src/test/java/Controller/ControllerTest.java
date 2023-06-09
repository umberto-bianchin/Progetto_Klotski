package Controller;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Controller
 */
public class ControllerTest extends KlotskiControllerTest {

    /**
     * Test the correct closure of the Database after closing the window
     */
    @Test
    public void testMousePressedController() {

        testedController.mousePressed(event);
        assertThrows(SQLException.class, () -> model.initState(2));

    }

    @Override
    protected UIController getTestedController() {
        return new Controller(model, view);
    }
}
