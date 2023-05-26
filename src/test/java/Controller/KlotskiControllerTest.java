package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * The KlotskiControllerTest class is an abstract class that serves as a base for controllers test
 */
abstract public class KlotskiControllerTest {

    protected static final KlotskiModel model = new KlotskiModel();
    protected static KlotskiUI view;
    protected static final JButton button =  new JButton();
    protected static final MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);
    protected static String message;
    protected static UIController testedController;

    /**
     * Set up method executed before each Controller test
     * Initializes the variable needed for the tests and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void initTestController() throws SQLException {

        model.initDatabase();
        // showMessage method override in KlotskiUI class to capture error messages
        view = new KlotskiUI() {
            @Override
            public void showMessage(String message, String title, int messageType) {
                KlotskiControllerTest.message = message;
            }
        };
    }

    @BeforeEach
    public void initController() {
        testedController = getTestedController();
    }

    /**
     * Start the Game based on the specified configuration
     * @return the Initial Positions of the Pieces
     * @throws SQLException if a database error occur
     */
    public Rectangle[] startGame(int configuration) throws SQLException {
        model.initState(configuration);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        return model.getCurrentPositions();
    }

    /**
     * Closes the database connection after the AuthListenerTest
     */
    @AfterAll
    public static void tearDownAll(){
        model.closeDatabaseConnection();
    }

    protected abstract UIController getTestedController();

}
