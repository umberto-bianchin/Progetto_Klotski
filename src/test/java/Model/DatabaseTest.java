package Model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Database
 */
public class DatabaseTest {

    //Data test
    private final LinkedList<Move> moves = new LinkedList<>(List.of(new Move(new Rectangle(0, 0, 100, 100), new Rectangle(100, 0, 100, 100))));
    private static final Rectangle[] finalPositions = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};
    private static Database db;

    /**
     * Set up method executed before all tests
     * Creates a new instance of the Database class
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        db = new Database();
    }

    /**
     * Tear down method executed after all tests
     * Close the database connection
     * @throws SQLException if there is an error in closing the database connection.
     */
    @AfterAll
    public static void tearDown() throws SQLException {
        db.closeConnection();
    }

    /**
     * Log out method executed after each test
     * Log out the user from the database
     */
    @AfterEach
    public void logOut() {
        db.resetIdPlayer();
    }

    /**
     * Test case for the saveGame() method
     * It verifies the behavior of saving a game in the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testSaveGame() throws SQLException, IllegalAccessException {
        //Test unauthorized save attempt
        assertThrows(IllegalAccessException.class, () -> db.saveGame(moves, 1, finalPositions, "Save Test", false));

        //Logging in and save operations
        db.login("JTest", "JTest");

        assertTrue(db.saveGame(moves, 1, finalPositions, "Database Test", false));

        //Trying to save a game with the same name
        assertFalse(db.saveGame(moves, 1, finalPositions, "Database Test", false));

        //Trying to save a resumed game
        assertTrue(db.saveGame(moves, 1, finalPositions, "Database Test", true));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getSavedMoves() method
     * It verifies the behavior of getting saved moves from the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testGetSavedMoves() throws SQLException, IllegalAccessException {
        //Test unauthorized get saved moves attempt
        assertThrows(IllegalAccessException.class, () -> db.getSavedMoves("Database Test"));

        //Logging in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, 1, finalPositions, "Database Test", false);

        //Verifying the retrieved move
        assertEquals(moves.get(0).getInitialPosition(), db.getSavedMoves("Database Test").get(0).getInitialPosition());
        assertEquals(moves.get(0).getFinalPosition(), db.getSavedMoves("Database Test").get(0).getFinalPosition());

        //Asserting that there are one saved move
        assertEquals(1, db.getSavedMoves("Database Test").size());

        //Getting moves from a game that does not exist to show that the LinkedList is empty
        assertEquals(0, db.getSavedMoves("Wrong name").size());

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getIdConf() method
     * It verifies the behavior of retrieving the id conf of a game from the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    public void testGetIdConf(int conf) throws SQLException, IllegalAccessException {
        //Testing unauthorized get id config attempt
        assertThrows(IllegalAccessException.class, () -> db.getIdConfiguration("Database Test"));

        //Logging in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, conf, finalPositions, "Database Test", false);

        //Testing get configuration from a game that does not exist
        assertThrows(SQLException.class, () -> db.getIdConfiguration("Wrong name"));

        assertEquals(conf, db.getIdConfiguration("Database Test"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getInitialPositions() method
     * It verifies the behavior of retrieving the initial positions of a game from the database
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void testGetInitialPositions() throws SQLException {
        //Preparing data test
        Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200),
                new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100),
                new Rectangle(0,400,100,100), new Rectangle(300,400,100,100)};

        assertArrayEquals(positions, db.getInitialPositions(0));
        //Testing that the method return an array of 10 null Rectangle if the configuration is wrong
        assertArrayEquals(new Rectangle[10], db.getInitialPositions(4));
    }

    /**
     * Test case for the getFinalPositions() method
     * It verifies the behavior of retrieving the final positions of a game from the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testGetFinalPositions() throws SQLException, IllegalAccessException {
        //Testing unauthorized get final config attempt
        assertThrows(IllegalAccessException.class, () -> db.getFinalPositions("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, 1, finalPositions, "Database Test", false);

        // TODO: 26/05/23 acua
        //assertArrayEquals(finalPositions, db.getFinalPositions("Database Test"));

        //Asserting that the method return an array of 10 null Rectangle if the name is wrong
        assertArrayEquals(new Rectangle[10], db.getFinalPositions("Wrong name"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getGameList() method
     * It verifies the behavior of retrieving the list of saved games from the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testGetGameList() throws SQLException, IllegalAccessException {
        //Testing unauthorized get game list attempt
        assertThrows(IllegalAccessException.class, () -> db.getGameList());

        db.login("JTest", "JTest");

        for(int i=0; i<3; i++){
            db.saveGame(moves, 1, finalPositions, ("Database Test"+i), false);
            assertEquals("Database Test" + i, db.getGameList().get(i));
        }

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the deleteGame() method
     * It verifies the behavior of deleting a saved games from the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testDeleteGame() throws SQLException, IllegalAccessException {
        //Testing unauthorized delete game attempt
        assertThrows(IllegalAccessException.class, () -> db.deleteGame("Database Test"));

        //Logging in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, 1, finalPositions, "Database Test", false);

        db.deleteGame("Database Test");
        assertEquals(0, db.getGameList().size());

        //Asserting that no exception is thrown by deleting a game with wrong name
        assertDoesNotThrow(()->db.deleteGame("Wrong name"));
    }

    /**
     * Test case for the login() method
     * It verifies the behavior of logging in a user in the database
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void testLogin() throws SQLException {
        //Trying to log in with wrong password
        assertFalse(db.login("JTest", "WrongPassword"));

        //Trying to log in with not registered username
        assertFalse(db.login("WrongUsername", "JTest"));

        //Logging in with correct credentials
        assertTrue(db.login("JTest", "JTest"));
    }

    /**
     * Test case for the registration() method
     * It verifies the behavior of registering a user in the database
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void testRegistration() throws SQLException, IllegalAccessException {
        //Trying to register with a username already registered
        assertFalse(db.registration("JTest", "JTest"));

        //Registering with correct credentials
        assertTrue(db.registration("JTest2", "JTest2"));

        //Logging in to delete user
        db.deleteUser();
    }

    /**
     * Test case for the deleteAllGames() method
     * It verifies the behavior of deleting all the saved games of a user
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testDeleteAllGames() throws SQLException, IllegalAccessException {
        //Test unauthorized delete all games attempt
        assertThrows(IllegalAccessException.class, () -> db.deleteAllGames());

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, 1, finalPositions, "Database Test1", false);
        db.saveGame(moves, 1, finalPositions, "Database Test2", false);

        db.deleteAllGames();
        assertEquals(0, db.getGameList().size());
    }

    /**
     * Test case for the deleteUser() method
     * It verifies the behavior of deleting a user
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void deleteUser() throws SQLException, IllegalAccessException {
        db.registration("User", "User");
        db.deleteUser();

        assertFalse(db.login("User", "User"));
    }

}