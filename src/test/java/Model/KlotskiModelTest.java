package Model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for KlotskiModel
 */
public class KlotskiModelTest {

    private static KlotskiModel klotskiModel;
    private static Rectangle[] initialPos;
    private static  Rectangle[] finalPos;
    private static final LinkedList<Move> moves = new LinkedList<>(List.of(new Move(new Rectangle(0,400,100,100), new Rectangle(100,400,100,100))));
    private static Database db;

    /**
     * Creates a new instance of the KlotskiModel class and Database class
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void setUp() throws SQLException, IllegalAccessException {
        klotskiModel = new KlotskiModel();
        klotskiModel.initDatabase();

        //initialize initalPos and finalPos from state and db object
        db = new Database();
        initialPos = db.getInitialPositions(0);

        State state = new State(initialPos,0);
        state.makeMove(moves.get(0));
        finalPos = state.getCurrentPositions();

        db.login("JTest", "JTest");
        db.saveGame(moves, 0, finalPos, "Model Test", false);

    }

    /**
     * Close the database connection after the test
     */
    @AfterAll
    public static void tearDown() throws SQLException, IllegalAccessException {
        klotskiModel.deleteAll();
        klotskiModel.closeDatabaseConnection();
        db.closeConnection();

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

        klotskiModel.logout();
        //Test unauthorized call attempt when not logged
        assertThrows(IllegalAccessException.class, () -> klotskiModel.db.getSavedMoves("Save Test"));
        klotskiModel.login("JTest", "JTest");
        klotskiModel.resumeState("Model Test");

        assertEquals(1, klotskiModel.getCounter());
        assertArrayEquals(initialPos, klotskiModel.state.initial_positions);
        assertArrayEquals(finalPos, klotskiModel.getCurrentPositions());
        assertEquals(0, klotskiModel.state.getIdConfiguration());

    }

    /**
     * Test case for the login() method
     * It verifies the behavior of logging-in in the database
     */
    @ParameterizedTest
    @CsvSource({",JTest", "JTest,", "JTest,Wrong Password"}) //empty field is interpreted as null
    public void testLogin(String username, String password) {
        assertThrows(RuntimeException.class, () -> klotskiModel.login(username, password));
    }

    /**
     * Test case for the registration() method
     * It verifies the behavior of registering in the database
     */
    @ParameterizedTest
    @CsvSource({",JTest", "JTest,", "'',JTest", "JTest,' '"}) //empty field is interpreted as null, '' as empty string
    public void testRegistrationException(String username, String password) {
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.registration(username, password));
        assertThrows(RuntimeException.class, () -> klotskiModel.registration("JTest", "JTest"));
    }

    /**
     * Test case for the undo() method
     */
    @Test
    public void undoTest(){

        klotskiModel.restartState();
        assertThrows(RuntimeException.class, () -> klotskiModel.undo());

    }

    /**
     * Test case for the deleteSavedGameTest() method
     */
    @Test
    public void deleteSavedGameTest() throws SQLException {
        klotskiModel.login("JTest", "JTest");
        assertThrows(RuntimeException.class, () -> klotskiModel.deleteSavedGame(null));
        assertDoesNotThrow(() -> klotskiModel.deleteSavedGame("not saved game"));

    }

    /**
     * Test case for the nextBestMove() method
     */
    @Test
    public void nextBestMoveTest() throws IOException, ParseException, SQLException {

        klotskiModel.initState(0);
        Move best;
        do {
            best = klotskiModel.nextBestMove();
        } while(!best.getFinalPosition().equals(new Rectangle(100,300,200,200)));

        assertTrue(klotskiModel.hasWin());

    }

    /**
     * Test case for the saveGame() method
     * It verifies the behavior of saving a game in the database
     */
    @ParameterizedTest
    @ValueSource(strings = {""," ", "Model Test"})
    public void testSaveGame(String name) throws SQLException {
        klotskiModel.initState(0);
        klotskiModel.login("JTest", "JTest");
        assertThrows(IllegalArgumentException.class, () -> klotskiModel.saveGame(name, false));
    }

}