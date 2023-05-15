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
        db.saveGame(moves, initialConf, finalConfig, "Database Test");

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

        //Getting moves from a game that does not exist to show that the LinkedList is empty
        assertEquals(0, db.getSavedMoves("Wrong name").size());

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
        assertThrows(IllegalAccessException.class, () -> db.getIdConfiguration("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, finalConfig, "Database Test");

        //Test get configuration from a game that does not exist
        assertThrows(SQLException.class, () -> db.getIdConfiguration("Wrong name"));

        assertEquals(initialConf, db.getIdConfiguration("Database Test"));
        assertNotEquals(2, db.getIdConfiguration("Database Test"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getInitialConfig() method.
     * It verifies the behavior of retrieving the initial config of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testGetInitialPositions() throws SQLException {
        //Prepare test data
        int initialConf = 0;
        Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                 new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
                new Rectangle(300,400,100,100)};


        Rectangle[] initialConfig = db.getInitialPositions(initialConf);

        for(int i=0; i<10; i++) {
            assertEquals(positions[i], initialConfig[i]);
            //Test that the method return an array of 10 null Rectangle if the configuration is wrong
            assertNull(db.getInitialPositions(4)[i]);
        }


    }

    /**
     * Test case for the getFinalConfig() method.
     * It verifies the behavior of retrieving the final config of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testGetFinalPositions() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] expectedFinalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized get final config attempt
        assertThrows(IllegalAccessException.class, () -> db.getFinalPositions("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, expectedFinalConfig, "Database Test");

        Rectangle[] finalConfig = db.getFinalPositions("Database Test");

        for(int i=0; i<2; i++)
            assertEquals(expectedFinalConfig[i], finalConfig[i]);

        for(int i=0; i<10; i++){
            //Test that the method return an array of 10 null Rectangle if the name is wrong
            assertNull(db.getFinalPositions("Wrong name")[i]);
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
        db.saveGame(moves, initialConf, finalConfig, "Database Test1");
        db.saveGame(moves, initialConf, finalConfig, "Database Test2");
        db.saveGame(moves, initialConf, finalConfig, "Database Test3");

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
        assertThrows(IllegalAccessException.class, () -> db.deleteGame("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, finalConfig, "Database Test1");
        db.saveGame(moves, initialConf, finalConfig, "Database Test2");

        Vector<String> savedGames = db.getGameList();
        assertEquals(2, savedGames.size());

        db.deleteGame("Database Test2");
        Vector<String> savedGames2 = db.getGameList();

        assertEquals("Database Test1", savedGames.get(0));
        //Accessing an invalid index to show there are only one saved game
        assertThrows(IndexOutOfBoundsException.class, () -> savedGames2.get(1));

        //Test that no exception is thrown by deleting a game with wrong name
        assertDoesNotThrow(()->db.deleteGame("Wrong name"));

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

        db.deleteUser();
    }

    /**
     * Test case for the deleteAllGames() method.
     * It verifies the behavior of deleting all the games saved of a user.
     * @throws SQLException if there is an error in the database operations.
     * @throws IllegalAccessException if there is an unauthorized database attempt.
     */
    @Test
    void testDeleteAllGames() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        moves.add(new Move(new Rectangle(100,100,100,100), new Rectangle(100,200,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};


        //Test unauthorized delete all games attempt
        assertThrows(IllegalAccessException.class, () -> db.deleteAllGames());

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, finalConfig, "Database Test1");
        db.saveGame(moves, initialConf, finalConfig, "Database Test2");

        db.deleteAllGames();
        assertEquals(0, db.getGameList().size());
    }

    /**
     * Test case for the deleteUser() method.
     * It verifies the behavior of deleting all the saved games of a user.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void deleteUser() throws SQLException {
        db.registration("User", "User");
        db.deleteUser();

        assertFalse(db.login("User", "User"));

    }
}