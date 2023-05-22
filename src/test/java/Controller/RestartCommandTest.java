package Controller;

import Model.KlotskiModel;
import Model.Move;
import View.KlotskiUI;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RestartCommandTest {

    /**
     * Test case for the mousePressed() method of RestartCommand class.
     * It verifies the behavior of restarting a game
     * @throws SQLException if there is an error in the database operations.
     * @throws IOException if there is an error in the solver.
     * @throws ParseException if there is an error in the solver.
     */
    @Test
    void testMousePressedRestart() throws SQLException, IOException, ParseException {
        //Prepare data test
        Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
                new Rectangle(300,400,100,100)};
        KlotskiModel model = new KlotskiModel();
        KlotskiUI view = new KlotskiUI();

        model.initDatabase();
        model.login("JTest", "JTest");
        view.initUser("JTest");
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        RestartCommand restart = new RestartCommand(model, view);

        //Make two moves to test the restart button
        Move move = model.nextBestMove();
        view.makeMove(move, model.getCounter());
        move = model.nextBestMove();
        view.makeMove(move, model.getCounter());

        JButton button = new JButton();
        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);

        //Assert the counter is zero
        restart.mousePressed(event);
        assertEquals(0, model.getCounter());

        //Assert the current configuration is equal to the initial configuration
        for(int i=0; i<10; i++){
            assertEquals(model.getCurrentPositions()[i].getX(), positions[i].getX());
            assertEquals(model.getCurrentPositions()[i].getY(), positions[i].getY());
            assertEquals(model.getCurrentPositions()[i].getWidth(), positions[i].getWidth());
            assertEquals(model.getCurrentPositions()[i].getHeight(), positions[i].getHeight());
        }

    }
}