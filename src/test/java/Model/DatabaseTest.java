package Model;

import org.junit.jupiter.api.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Database
 */
public class DatabaseTest {

    private Database db;

    /**
     * Set up method executed before each test
     * Creates a new instance of the Database class
     * @throws SQLException if there is an error in establishing the database connection
     */
    @BeforeEach
    public void setUp() throws SQLException {
        db = new Database();
    }

    /**
     * Tear down method executed after each test
     * Close the database connection
     * @throws SQLException if there is an error in closing the database connection.
     */
    @AfterEach
    public void tearDown() throws SQLException {
        db.closeConnection();
    }

    /**
     * Test case for the saveGame() method
     * It verifies the behavior of saving a game in the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testSaveGame() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        moves.add(new Move(new Rectangle(100,100,100,100), new Rectangle(100,200,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized save attempt
        assertThrows(IllegalAccessException.class, () -> db.saveGame(moves, initialConf, finalConfig, "Save Test", false));

        //Log in and save operations
        db.login("JTest", "JTest");

        assertTrue(db.saveGame(moves, initialConf, finalConfig, "Database Test", false));

        //Trying to save a game with the same name
        assertFalse(db.saveGame(moves, initialConf, finalConfig, "Database Test", false));

        //Trying to save a resumed game
        moves.add(new Move(new Rectangle(100,100,100,100), new Rectangle(200,100,100,100)));
        assertTrue(db.saveGame(moves, initialConf, finalConfig, "Database Test", true));

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
        db.saveGame(moves, initialConf, finalConfig, "Database Test", false);

        //Prepare expected results
        Rectangle rectangle1 = new Rectangle(0,0,100,100);
        Rectangle rectangle2 = new Rectangle(100,0,100,100);
        Rectangle rectangle3 = new Rectangle(100,100,100,100);
        Rectangle rectangle4 = new Rectangle(100,200,100,100);

        //Verify the retrieved moves
        assertEquals(rectangle1, db.getSavedMoves("Database Test").get(0).getInitialPosition());
        assertEquals(rectangle2, db.getSavedMoves("Database Test").get(0).getFinalPosition());
        assertEquals(rectangle3, db.getSavedMoves("Database Test").get(1).getInitialPosition());
        assertEquals(rectangle4, db.getSavedMoves("Database Test").get(1).getFinalPosition());

        //Asserting that there are two saved moves
        assertEquals(2, db.getSavedMoves("Database Test").size());

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
    @Test
    public void testGetIdConf() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized get id config attempt
        assertThrows(IllegalAccessException.class, () -> db.getIdConfiguration("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, finalConfig, "Database Test", false);

        //Test get configuration from a game that does not exist
        assertThrows(SQLException.class, () -> db.getIdConfiguration("Wrong name"));

        assertEquals(initialConf, db.getIdConfiguration("Database Test"));
        assertNotEquals(2, db.getIdConfiguration("Database Test"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getInitialConfig() method
     * It verifies the behavior of retrieving the initial config of a game from the database
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void testGetInitialPositions() throws SQLException {
        //Prepare test data
        int initialConf = 0;
        Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200),
                new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100),
                new Rectangle(0,400,100,100), new Rectangle(300,400,100,100)};

        for(int i=0; i<10; i++) {
            assertEquals(positions[i], db.getInitialPositions(initialConf)[i]);
            //Test that the method return an array of 10 null Rectangle if the configuration is wrong
            assertNull(db.getInitialPositions(4)[i]);
        }
    }

    /**
     * Test case for the getFinalConfig() method
     * It verifies the behavior of retrieving the final config of a game from the database
     * @throws SQLException if there is an error in the database operations
     * @throws IllegalAccessException if there is an unauthorized database attempt
     */
    @Test
    public void testGetFinalPositions() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] expectedFinalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized get final config attempt
        assertThrows(IllegalAccessException.class, () -> db.getFinalPositions("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, expectedFinalConfig, "Database Test", false);


        for(int i=0; i<2; i++)
            assertEquals(expectedFinalConfig[i], db.getFinalPositions("Database Test")[i]);

        for(int i=0; i<10; i++){
            //Asserting that the method return an array of 10 null Rectangle if the name is wrong
            assertNull(db.getFinalPositions("Wrong name")[i]);
        }

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
        db.saveGame(moves, initialConf, finalConfig, "Database Test1", false);
        db.saveGame(moves, initialConf, finalConfig, "Database Test2", false);
        db.saveGame(moves, initialConf, finalConfig, "Database Test3", false);

        for(int i=0; i<3; i++){
            assertEquals(expectedSavedGames[i], db.getGameList().get(i));
        }

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
    public void testDeleteGame() throws SQLException, IllegalAccessException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Test unauthorized delete game attempt
        assertThrows(IllegalAccessException.class, () -> db.deleteGame("Database Test"));

        //Log in and save data
        db.login("JTest", "JTest");
        db.saveGame(moves, initialConf, finalConfig, "Database Test1", false);
        db.saveGame(moves, initialConf, finalConfig, "Database Test2", false);

        //Asserting that there are two games saved in the database
        assertEquals(2, db.getGameList().size());

        db.deleteGame("Database Test2");

        //Asserting that there is only one game saved in the database
        assertEquals("Database Test1", db.getGameList().get(0));

        assertEquals(1, db.getGameList().size());

        //Asserting that no exception is thrown by deleting a game with wrong name
        assertDoesNotThrow(()->db.deleteGame("Wrong name"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the login() method
     * It verifies the behavior of logging in a user in the database
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void testLogin() throws SQLException {
        //Try to log in with wrong password
        assertFalse(db.login("JTest", "WrongPassword"));

        //Try to log in with not registered username
        assertFalse(db.login("WrongUsername", "JTest"));

        //Log in with correct credentials
        assertTrue(db.login("JTest", "JTest"));
    }

    /**
     * Test case for the registration() method
     * It verifies the behavior of registering a user in the database
     * @throws SQLException if there is an error in the database operations
     */
    @Test
    public void testRegistration() throws SQLException, IllegalAccessException {
        //Try to register with a username already registered
        assertFalse(db.registration("JTest", "JTest"));

        //Registration with correct credentials
        assertTrue(db.registration("JTest2", "JTest2"));

        //Log in to delete user
        db.login("User", "User");
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
        db.saveGame(moves, initialConf, finalConfig, "Database Test1", false);
        db.saveGame(moves, initialConf, finalConfig, "Database Test2", false);
        assertEquals(2, db.getGameList().size());

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

        assertTrue(db.login("User", "User"));

        db.login("User", "User");
        db.deleteUser();

        assertFalse(db.login("User", "User"));
    }

}