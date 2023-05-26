package Model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Solver
 */
public class SolverTest {

    private static Solver solver;
    private static Rectangle[] initialPos;

    /**
     * Set up method executed before each test
     * Creates a new instance of the Solver class, and get from the first configuration the initial positions
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        solver = new Solver();
        KlotskiModel model = new KlotskiModel();
        model.initDatabase();
        model.initState(0);
        initialPos = model.getCurrentPositions();
    }

    @Nested
    class NextBestMoveTest{

        /**
         * Test case for the nextBestMove() method
         * @throws IOException if an I/O error occurs while making the API request
         * @throws ParseException when the received JSON is invalid
         */
        @Test
        public void testNextBestMove() throws IOException, ParseException {
            // Define the test configuration
            Move move = solver.nextBestMove(initialPos);

            //Asserting the solver move is equal to the expected move
            Move expectedMove = new Move(new Rectangle(100, 300, 100, 100), new Rectangle(100, 400, 100, 100)); //move get directly from the API doc
            assertEquals(expectedMove.getInitialPosition(), move.getInitialPosition());
            assertEquals(expectedMove.getFinalPosition(), move.getFinalPosition());

        }


        /**
         * Test case for the nextBestMove() method with wrong initial positions
         */
        @Test
        public void testNextBestMoveWrongPos() {

            //add a rectangle to the initial position
            Rectangle[] invalidPositions = Arrays.copyOf(initialPos, initialPos.length + 1);
            invalidPositions[invalidPositions.length - 1] = new Rectangle(0,400,100,100);
            assertThrows(IllegalArgumentException.class, () -> solver.nextBestMove(invalidPositions));
        }

    }





}