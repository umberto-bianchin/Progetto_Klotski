package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class KlotskiModelTest {

    private KlotskiModel klotskiModel;
    Rectangle[] initialPos = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
            new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
            new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
            new Rectangle(300,400,100,100)};
    Rectangle[] finalPos = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
            new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
            new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(100,400,100,100),
            new Rectangle(300,400,100,100)};

    /**
     * Set up method executed before each test.
     * Creates a new instance of the KlotskiModel class and Database class.
     * @throws SQLException if there is an error in establishing the database connection.
     */
    @BeforeEach
    void setUp() throws SQLException {
        klotskiModel = new KlotskiModel();
        klotskiModel.initDatabase();
    }

    /**
     * Tear down method executed after each test
     * Close the database connection
     */
    @AfterEach
    void tearDown() {
        klotskiModel.closeDatabaseConnection();
    }

    /**
     * Test case for the initState() method.
     * It verifies the behavior of initializing a new state.
     */
    @Test
    void testInitState() {
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.initState(4));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.initState(-1));
    }

    /**
     * Test case for the resumeState() method.
     * It verifies the behavior of resuming a state.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testResumeState() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,400,100,100), new Rectangle(100,400,100,100)));
        int initialConf = 0;

        //Test unauthorized call attempt
        assertThrows(IllegalAccessException.class, () -> klotskiModel.db.getSavedMoves("Save Test"));

        //Log in and save data
        klotskiModel.db.login("JTest", "JTest");
        klotskiModel.db.saveGame(moves, initialConf, finalPos, "Model Test");

        klotskiModel.resumeState("Model Test");

        assertEquals(1, klotskiModel.state.getMoves().size());
        for(int i=0; i<10; i++){
            assertEquals(initialPos[i], klotskiModel.state.initial_positions[i]);
            assertEquals(finalPos[i], klotskiModel.state.pieces[i].getPosition());
            assertEquals(initialConf, klotskiModel.state.getIdConfiguration());
        }

        klotskiModel.db.deleteAllGames();
    }

    /**
     * Test case for the login() method.
     * It verifies the behavior of logging-in in the database.
     */
    @Test
    void testLogin() {
        assertThrows(RuntimeException.class, () -> klotskiModel.login(null, "JTest"));
        assertThrows(RuntimeException.class, () -> klotskiModel.login("JTest", null));
        assertThrows(RuntimeException.class, () -> klotskiModel.login("JTest", "Wrong Password"));
    }

    /**
     * Test case for the registration() method.
     * It verifies the behavior of registering in the database.
     */
    @Test
    void testRegistration() {
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration(null, "JTest"));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration("JTest", null));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration("", "JTest"));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration("JTest", ""));

        assertThrows(RuntimeException.class, () -> klotskiModel.registration("JTest", "JTest"));
    }

    /**
     * Test case for the saveGame() method.
     * It verifies the behavior of saving a game in the database.
     */
    @Test
    void testSaveGame() throws SQLException, IllegalAccessException {
        LinkedList<Move> moves = new LinkedList<>();
        int initialConf = 0;

        //Log in and init state
        klotskiModel.db.login("JTest", "JTest");
        klotskiModel.initState(initialConf);
        klotskiModel.db.saveGame(moves, initialConf, finalPos, "Model Test");

        assertThrows(IllegalArgumentException.class, () -> klotskiModel.saveGame(""));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.saveGame("Model Test"));

        klotskiModel.db.deleteAllGames();
    }

}