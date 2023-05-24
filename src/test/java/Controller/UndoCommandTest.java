package Controller;

import Model.KlotskiModel;
import Model.Move;
import View.KlotskiUI;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UndoCommand
 */
public class UndoCommandTest {
    private static KlotskiModel model;
    private static KlotskiUI view;
    private static UndoCommand undo;
    private final Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200),
            new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
            new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
            new Rectangle(100,300,100,100), new Rectangle(200,300,100,100),
            new Rectangle(0,400,100,100), new Rectangle(300,400,100,100)};

    /**
     * Set up method executed before all test
     * Initializes the necessary variables and sets up the KlotskiModel and KlotskiUI instances
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        model = new KlotskiModel();
        view = new KlotskiUI();

        model.initDatabase();
        model.login("JTest", "JTest");
        view.initUser("JTest");
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        undo = new UndoCommand(model, view);
    }

    /**
     * Tear down method executed after all tests
     * Closes the database connection
     */
    @AfterAll
    public static void tearDown(){
        model.closeDatabaseConnection();
    }

    /**
     * Test case for the mousePressed() method of UndoCommand class
     * It verifies the behavior of undoing a move
     * @throws IOException if there is an error in the solver
     * @throws ParseException if there is an error in the solver
     */
    @Test
    public void testMousePressedUndo() throws IOException, ParseException {
        //Make two moves to test the undo button
        Move move = model.nextBestMove();
        view.makeMove(move, model.getCounter());
        move = model.nextBestMove();
        view.makeMove(move, model.getCounter());

        JButton button = new JButton();
        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);

        //Assert that the counter is decreased by one
        undo.mousePressed(event);
        assertEquals(1, model.getCounter());

        //Assert that the counter is decreased by one
        undo.mousePressed(event);
        assertEquals(0, model.getCounter());

        //Assert the current configuration is equal to the initial configuration (after two undo)
        for(int i=0; i<10; i++){
            assertEquals(model.getCurrentPositions()[i].getX(), positions[i].getX());
            assertEquals(model.getCurrentPositions()[i].getY(), positions[i].getY());
            assertEquals(model.getCurrentPositions()[i].getWidth(), positions[i].getWidth());
            assertEquals(model.getCurrentPositions()[i].getHeight(), positions[i].getHeight());
        }
    }

}