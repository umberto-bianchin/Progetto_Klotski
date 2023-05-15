package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database db;

    /**
     * Set up method executed before each test.
     * Creates a new instance of the Database class.
     * @throws SQLException if there is an error in establishing the database connection.
     */
    @BeforeEach
    void setUp() throws SQLException {
            db = new Database();
    }

    /**
     * Tear down method executed after each test
     * Close the database connection
     * @throws SQLException if there is an error in closing the database connection.
     */
    @AfterEach
    void tearDown() throws SQLException {
        db.closeConnection();
    }

    /**
     * Test case for the saveGame() method.
     * It verifies the behavior of saving a game in the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testSaveGame() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        moves.add(new Move(new Rectangle(100,100,100,100), new Rectangle(100,200,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized save attempt
        assertThrows(IllegalAccessException.class, () -> db.saveGame(moves, initialConf, finalConfig, "Save Test"));

        //Log in and save operations
        db.login("JTest", "JTest");

        assertTrue(db.saveGame(moves, initialConf, finalConfig, "Database Test"));
        //Try to save a game with the same name
        assertFalse(db.saveGame(moves, initialConf, finalConfig, "Database Test"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getSavedMoves() method.
     * It verifies the behavior of getting saved moves from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testGetSavedMoves() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        moves.add(new Move(new Rectangle(100,100,100,100), new Rectangle(100,200,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized get saved moves attempt
        assertThrows(IllegalAccessException.class, () -> db.getSavedMoves("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, finalConfig, "Database Test");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //Prepare expected results
        Rectangle rectangle1 = new Rectangle(0,0,100,100);
        Rectangle rectangle2 = new Rectangle(100,0,100,100);
        Rectangle rectangle3 = new Rectangle(100,100,100,100);
        Rectangle rectangle4 = new Rectangle(100,200,100,100);

        LinkedList<Move> retrieveMoves = db.getSavedMoves("Database Test");

        //Verify the retrieved moves
        assertEquals(rectangle1, retrieveMoves.get(0).getInitialPosition());
        assertEquals(rectangle2, retrieveMoves.get(0).getFinalPosition());
        assertEquals(rectangle3, retrieveMoves.get(1).getInitialPosition());
        assertEquals(rectangle4, retrieveMoves.get(1).getFinalPosition());

        //Accessing an invalid index to show there are only the two saved moves
        assertThrows(IndexOutOfBoundsException.class, () -> retrieveMoves.get(2));

        //Clean up saved games
        db.deleteAllGames();

    }

    /**
     * Test case for the getIdConf() method.
     * It verifies the behavior of retrieving the id conf of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testGetIdConf() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized get id config attempt
        assertThrows(IllegalAccessException.class, () -> db.getIdConf("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, finalConfig, "Database Test");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertEquals(initialConf, db.getIdConf("Database Test"));
        assertNotEquals(2, db.getIdConf("Database Test"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getInitialConfig() method.
     * It verifies the behavior of retrieving the initial config of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testGetInitialConfig() throws SQLException, IllegalAccessException {
        //Prepare test data
        int initialConf = 0;
        Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                 new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
                new Rectangle(300,400,100,100)};


        Rectangle[] initialConfig = db.getInitialConfig(initialConf);

        for(int i=0; i<10; i++)
            assertEquals(positions[i], initialConfig[i]);

    }

    /**
     * Test case for the getFinalConfig() method.
     * It verifies the behavior of retrieving the final config of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testGetFinalConfig() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] expectedFinalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized get final config attempt
        assertThrows(IllegalAccessException.class, () -> db.getFinalConfig("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, expectedFinalConfig, "Database Test");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Rectangle[] finalConfig = db.getFinalConfig("Database Test");

        for(int i=0; i<2; i++)
            assertEquals(expectedFinalConfig[i], finalConfig[i]);

        //Clean up saved games
        db.deleteAllGames();

    }

    /**
     * Test case for the getGameList() method.
     * It verifies the behavior of retrieving the list of saved games from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testGetGameList() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};
        String[] expectedSavedGames = {"Database Test1", "Database Test2", "Database Test3"};

        //Test unauthorized get game list attempt
        assertThrows(IllegalAccessException.class, () -> db.getGameList());

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, finalConfig, "Database Test1");
            db.saveGame(moves, initialConf, finalConfig, "Database Test2");
            db.saveGame(moves, initialConf, finalConfig, "Database Test3");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Vector<String> savedGames = db.getGameList();

        for(int i=0; i<3; i++){
            assertEquals(expectedSavedGames[i], savedGames.get(i));
        }

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getGameList() method.
     * It verifies the behavior of retrieving the list of saved games from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testDeleteGame() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized delete game attempt
        assertThrows(IllegalAccessException.class, () -> db.getGameList());

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, finalConfig, "Database Test1");
            db.saveGame(moves, initialConf, finalConfig, "Database Test2");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Vector<String> savedGames = db.getGameList();
        assertEquals(2, savedGames.size());

        db.deleteGame("Database Test2");
        Vector<String> savedGames2 = db.getGameList();

        assertEquals("Database Test1", savedGames.get(0));
        //Accessing an invalid index to show there are only one saved game
        assertThrows(IndexOutOfBoundsException.class, () -> savedGames2.get(1));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the login() method.
     * It verifies the behavior of loggin in a user in the database.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testLogin() throws SQLException {
        //Try to log in with wrong password
        assertFalse(db.login("JTest", "WrongPassword"));

        //Try to log in with not registered username
        assertFalse(db.login("WrongUsername", "JTest"));

        //Log in with correct credentials
        assertTrue(db.login("JTest", "JTest"));

    }

    /**
     * Test case for the registration() method.
     * It verifies the behavior of registering a user in the database.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testRegistration() throws SQLException {
        //Try to register with a username already created
        assertFalse(db.registration("JTest", "JTest"));

        //Registration with correct credentials
        assertTrue(db.registration("JTest2", "JTest2"));

        db.deleteUser("JTest2");

    }
}