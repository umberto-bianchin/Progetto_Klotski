package Controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ConfigurationListener
 */
class ConfigurationListenerTest extends KlotskiControllerTest{

    /**
     * Test that the correct configuration is chosen, comparing the initial positions of each
     * @param conf configuration id to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2", "3"})
    void mousePressed(String conf) throws SQLException {

        Rectangle[] positions = startGame(Integer.parseInt(conf));
        button.setName(conf);
        testedController.mousePressed(event);
        assertArrayEquals(positions, model.getCurrentPositions());

    }

    /**
     * Test the error message choosing an invalid configuration
     * @param conf invalid configuration to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"-1", "5"})
    void mousePressedWrong(String conf) {

        button.setName(conf);
        testedController.mousePressed(event);
        assertEquals(message, "id_configuration invalid");

    }

    @Override
    protected UIController getTestedController() {
        return new ConfigurationListener(model, view);
    }

}