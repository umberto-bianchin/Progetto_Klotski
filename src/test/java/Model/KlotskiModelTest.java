package Model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for KlotskiModel
 */
public class KlotskiModelTest {

    private static KlotskiModel klotskiModel;
    private static final Rectangle[] initialPos = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200),
            new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
            new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
            new Rectangle(100,300,100,100), new Rectangle(200,300,100,100),
            new Rectangle(0,400,100,100), new Rectangle(300,400,100,100)};
    private static final Rectangle[] finalPos = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200),
            new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
            new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
            new Rectangle(100,300,100,100), new Rectangle(200,300,100,100),
            new Rectangle(100,400,100,100), new Rectangle(300,400,100,100)};

    /**
     * Creates a new instance of the KlotskiModel class and Database class
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        klotskiModel = new KlotskiModel();
        klotskiModel.initDatabase();
    }

    /**
     * Close the database connection after the test
     */
    @AfterAll
    public static void tearDown() {
        klotskiModel.closeDatabaseConnection();
    }

    /**
     * Test case for the initState() method
     * It verifies the behavior of initializing a new state
     */
    @ParameterizedTest
    @ValueSource(strings = {"-1", "5"})
    public void testInitState(int conf) {
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.initState(conf));
    }

    /**
     * Test case for the resumeState() method
     * It verifies the behavior of resuming a state
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testResumeState() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,400,100,100), new Rectangle(100,400,100,100)));
        int initialConf = 0;

        //Test unauthorized call attempt when not logged
        assertThrows(IllegalAccessException.class, () -> klotskiModel.db.getSavedMoves("Save Test"));

        //Log in and save data
        klotskiModel.db.login("JTest", "JTest");
        klotskiModel.db.saveGame(moves, initialConf, finalPos, "Model Test", false);
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
     * Test case for the login() method
     * It verifies the behavior of logging-in in the database
     */
    @Test
    public void testLogin() {
        assertThrows(RuntimeException.class, () -> klotskiModel.login(null, "JTest"));
        assertThrows(RuntimeException.class, () -> klotskiModel.login("JTest", null));
        assertThrows(RuntimeException.class, () -> klotskiModel.login("JTest", "Wrong Password"));
    }

    /**
     * Test case for the registration() method
     * It verifies the behavior of registering in the database
     */
    @Test
    public void testRegistration() {
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration(null, "JTest"));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration("JTest", null));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration("", "JTest"));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration("JTest", ""));

        assertThrows(RuntimeException.class, () -> klotskiModel.registration("JTest", "JTest"));
    }

    /**
     * Test case for the saveGame() method
     * It verifies the behavior of saving a game in the database
     */
    @Test
    public void testSaveGame() throws SQLException, IllegalAccessException {
        //Log in and init state
        klotskiModel.db.login("JTest", "JTest");
        klotskiModel.initState(0);
        klotskiModel.db.saveGame(new LinkedList<>(), 0, finalPos, "Model Test", false);

        assertThrows(IllegalArgumentException.class, () -> klotskiModel.saveGame("", false));
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.saveGame("Model Test", false));

        klotskiModel.db.deleteAllGames();
    }

}