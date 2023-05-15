package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

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
     */
    @Test
    void testSaveGame() throws SQLException {
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
        try {
            assertTrue(db.saveGame(moves, initialConf, finalConfig, "Save Test"));
            //Save game with the same name
            assertFalse(db.saveGame(moves, initialConf, finalConfig, "Save Test"));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //Clean up saved games
        db.deleteAllGames();

    }

    /**
     * Test case for the getSavedMoves() method.
     * It verifies the behavior of getting saved moves from the database.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testGetSavedMoves() throws SQLException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        moves.add(new Move(new Rectangle(100,100,100,100), new Rectangle(100,200,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, finalConfig, "Save Test");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //Prepare expected results
        Rectangle rectangle1 = new Rectangle(0,0,100,100);
        Rectangle rectangle2 = new Rectangle(100,0,100,100);
        Rectangle rectangle3 = new Rectangle(100,100,100,100);
        Rectangle rectangle4 = new Rectangle(100,200,100,100);

        LinkedList<Move> retrieveMoves = db.getSavedMoves("Save Test");

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
     */
    @Test
    void testGetIdConf() throws SQLException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] finalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, finalConfig, "Save Test");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertEquals(initialConf, db.getIdConf("Save Test"));
        assertNotEquals(2, db.getIdConf("Save Test"));

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getInitialConfig() method.
     * It verifies the behavior of retrieving the initial config of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testGetInitialConfig() throws SQLException {
        //Prepare test data
        int initialConf = 0;
        Rectangle[] positions = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                 new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
                new Rectangle(300,400,100,100)};


        Rectangle[] initialConfig = db.getInitialConfig(initialConf);

        for(int i=0; i<10; i++)
            assertEquals(positions[i], initialConfig[i]);

        //Clean up saved games
        db.deleteAllGames();
    }

    /**
     * Test case for the getFinalConfig() method.
     * It verifies the behavior of retrieving the final config of a game from the database.
     * @throws SQLException if there is an error in the database operations.
     */
    @Test
    void testGetFinalConfig() throws SQLException {
        //Prepare test data
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(new Rectangle(0,0,100,100), new Rectangle(100,0,100,100)));
        int initialConf = 1;
        Rectangle[] expectedFinalConfig = {new Rectangle(100, 0, 100, 100), new Rectangle(100, 200, 100, 100)};

        //Log in and save data
        db.login("JTest", "JTest");
        try {
            db.saveGame(moves, initialConf, expectedFinalConfig, "Save Test");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Rectangle[] finalConfig = db.getFinalConfig("Save Test");

        for(int i=0; i<2; i++)
            assertEquals(expectedFinalConfig[i], finalConfig[i]);

        //Clean up saved games
        db.deleteAllGames();


    }

    @Test
    void testGetGameList() {
    }

    @Test
    void testDeleteGame() {
    }

    @Test
    void testLogin() {
    }

    @Test
    void testRegistration() {
    }
}