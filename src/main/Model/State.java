package Model;

import java.awt.*;
import java.util.LinkedList;

/**
 * The State class provides the information on the current game and the method associated to play
 */
class State {

    LinkedList<Move> moves;
    Rectangle[] initial_positions;
    Piece[] pieces;
    private Piece selectedPiece = null;
    private boolean win = false;
    private int id_configuration;

    /**
     * @param initial_positions Array[10] of initial pieces position
     * @param id_configuration int representing the initial configuration (0-4)
     */
    public State(Rectangle[] initial_positions, int id_configuration){

        initAttributes(initial_positions, initial_positions, id_configuration);
        moves = new LinkedList<>();

    }

    /**
     * @param saved_moves LinkedList of saved moves
     * @param initial_positions Array[10] of initial pieces position
     * @param saved_positions Array[10] of saved pieces position
     * @param id_configuration int representing the initial configuration (0-4)
     */
    public State(LinkedList<Move> saved_moves, Rectangle[] initial_positions, Rectangle[] saved_positions, int id_configuration){

        initAttributes(initial_positions, saved_positions, id_configuration);
        moves = saved_moves;

    }

    /**
     * Initialize object attributes, with controls on the arguments
     */
    private void initAttributes(Rectangle[] initial_positions, Rectangle[] current_positions, int id_configuration) {

        if(initial_positions.length != 10 || current_positions.length != 10)
            throw new IllegalArgumentException("positions array invalid");

        if(id_configuration<0 || id_configuration>=4)
            throw new IllegalArgumentException("configuration_id invalid");

        this.initial_positions = initial_positions;
        this.id_configuration = id_configuration;
        pieces = new Piece[10];

        for(int i=0; i<10; i++){
            pieces[i] = new Piece(current_positions[i]);
        }
    }

    /**
     * @return Array[10] of the current pieces positions
     */
    public Rectangle[] getCurrentPositions(){

        Rectangle[] currentPos = new Rectangle[10];
        for(int i=0; i<10; i++)
            currentPos[i] = pieces[i].getPosition();
        return currentPos;
    }

    public int getIdConfiguration(){
        return id_configuration;
    }

    /**
     * @return LinkedList with the performed ordered moves
     */
    public LinkedList<Move> getMoves(){
        return moves;
    }

    public Rectangle[] getInitialPositions(){
        return initial_positions;
    }

    /**
     * @param p Point contained by the desired pieces, if null reset the selected piece
     */
    public void setSelectedPiece(Point p) {

        if (p == null) {
            selectedPiece = null;
            return;
        }

        for (Piece piece : pieces) {
            if (piece.contains(p))
                selectedPiece = piece;
        }
    }

    /**
     * Move the selected piece to a location that contains the Point p
     * @return Final move
     * @throws RuntimeException when the move is illegal or no piece is selected
     */
    public Move moveSelectedPiece(Point p) throws RuntimeException{

        if(selectedPiece == null)
            throw new RuntimeException("No piece selected");

        // check which available position contain the point p, null if there isn't
        Rectangle possiblePosition = selectedPiece.checkAvailable(p);
        Rectangle window = new Rectangle(0, 0, 400, 500);

        // throws exception when possible position is null, or it isn't contained in the game windows
        if (possiblePosition == null || !window.contains(possiblePosition))
            throw new RuntimeException();

        // throws exception if the possible position intersects with another piece
        for (Piece piece : pieces) {
            if (piece != selectedPiece && piece.intersection(possiblePosition)) {
                throw new RuntimeException();
            }
        }

        Move move = new Move(selectedPiece.getPosition(),possiblePosition);
        moves.add(move);

        win = selectedPiece.move(possiblePosition);
        selectedPiece = null;

        return move;

    }

    public boolean getWin(){return win;}

    /**
     * Undo the last move
     * @return last move reversed
     */
    public Move undo(){

        Move lastMoveRev = moves.getLast().reverse();
        makeMove(lastMoveRev);

        moves.removeLast(); // remove the reversed move added
        moves.removeLast(); // remove the moves wanted to be deleted

        return lastMoveRev;

    }

    /**
     * Make the specified move
     */
    void makeMove(Move move){
        setSelectedPiece(move.getInitialPosition().getLocation());
        win = selectedPiece.move(move.getFinalPosition());
        selectedPiece = null;
        moves.add(move);
    }

}
