package Model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    private Solver solver;

    /**
     * Set up method executed before each test.
     * Creates a new instance of the Solver class.
     */
    @BeforeEach
    public void setUp(){
        solver = new Solver();
    }

    /**
     * Test case for the nextBestMove() method.
     * @throws IOException if an I/O error occurs while making the API request
     * @throws ParseException when the received JSON is invalid
     */
    @Test
    public void testNextBestMove() throws IOException, ParseException {
        // Define the test configuration
        Rectangle[] initialPos = {new Rectangle(100,0,200,200),new Rectangle(0,0,100,200), new Rectangle(300,0,100,200), new Rectangle(0,200,100,200),
                new Rectangle(300,200,100,200), new Rectangle(100,200,200,100),
                new Rectangle(100,300,100,100), new Rectangle(200,300,100,100), new Rectangle(0,400,100,100),
                new Rectangle(300,400,100,100)};

        Move move = solver.nextBestMove(initialPos);

        // Verify the expected move
        Move expectedMove = new Move(new Rectangle(100, 300, 100, 100), new Rectangle(100, 400, 100, 100));
        assertEquals(expectedMove.getInitialPosition(), move.getInitialPosition());
        assertEquals(expectedMove.getFinalPosition(), move.getFinalPosition());
    }

    /**
     * Test case for the setConfigurationHash() method.
     * It verifies the behavior of setting the configuration hash of a solver.
     */
    @Test
    public void testSetConfigurationHash() {
        long hash = 1234L;
        solver.setConfigurationHash(hash);
        assertEquals(hash, solver.hashCurrentConf);
    }
}