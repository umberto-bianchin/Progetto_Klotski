package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SaveCommandTest {

    static int n = 0;
    String correctlySaved;
    String notLogged;
    String blankName;


    /**
     * Test case for the mousePressed() method of DisconnectionListener class.
     * It verifies the behavior of pressing a button with disconnection Listener.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if the player is not logged in to the system
     */
    @Test
    void testMousePressedSaveCommand() throws SQLException, IllegalAccessException {
        //Prepare data test
        KlotskiModel model = new KlotskiModel();
        model.initDatabase();

        // askGameName, showMessage and showAuthenticationDialog methods override in KlotskiUI class to set the state variables
        KlotskiUI view = new KlotskiUI() {
            @Override
            public String askGameName() {
                if(n == 0)
                    return "";

                return "Test";

            }
            @Override
            public void showMessage(String message, String title, int type) {
                if(n == 0){
                    n++;
                    blankName = message;
                }
                else
                    correctlySaved = message;
            }
            @Override
            public boolean showAuthenticationDialog() {
                notLogged = "Dialog displayed correctly";
                return false;
            }
        };

        model.login("JTest", "JTest");
        view.initUser("JTest");
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());

        //Saving correctly the game
        SaveCommand save = new SaveCommand(model, view);
        JButton button = new JButton();
        MouseEvent event = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);
        save.mousePressed(event);
        assertEquals("You can't save match with blank names", blankName);
        assertEquals("Successfully saved the game", correctlySaved);
        model.deleteAll();

        //Try to save without being authenticated
        model.logout();
        view.logout();
        model.initState(0);
        view.initGame(model.getCurrentPositions(), model.getCounter());
        save.mousePressed(event);
        assertEquals("Dialog displayed correctly", notLogged);

    }
}