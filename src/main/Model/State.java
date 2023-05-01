package Model;

import java.awt.*;
import java.util.LinkedList;

class State {

    LinkedList<Move> moves;
    Rectangle[] initial_config;
    Piece[] current_config;
    private Piece selectedPiece;
    private int counter = 0;
    private boolean win=false;
    private int initial_conf;



    // nuova partita => configurazione iniziale
    public State(Rectangle[] config, int conf){
        initial_config = config;
        initial_conf = conf;
        moves = new LinkedList<>();

        current_config = new Piece[10];
        for(int i=0; i<10; i++){
            current_config[i] = new Piece(config[i]);
        }

    }

    //partita iniziata => mosse, configurazione iniziale (per il reset) e finale
    public State(LinkedList<Move> saved_moves, Rectangle[] starting_config, Rectangle[] saved_config, int conf){
        moves = saved_moves;
        initial_conf = conf;
        initial_config = starting_config;

        current_config = new Piece[10];
        for(int i=0; i<10; i++){
            current_config[i] = new Piece(saved_config[i]);
        }
    }

    public void setCurrentConfig(Rectangle[] saved_config){
        for(int i=0; i<10; i++){
            current_config[i].setPosition(saved_config[i]);
        }
    }

    public Rectangle[] getInitialPositions(){
        return initial_config;
    }

    public void setCounter(int i){
        counter = i;
    }

    public int getCounter(){
        return counter;
    }

    public void setSelectedPiece(Point p) {

        if(p==null){
            selectedPiece = null;
            return;
        }

        for(Piece piece : current_config) {
            if(piece.contains(p))
                selectedPiece = piece;
        }
    }

    public boolean checkIntersection(Rectangle possiblePosition){

        for (Piece piece : current_config) {
            if (piece != selectedPiece && piece.intersection(possiblePosition)) {
                return true;
            }
        }

        return false;
    }

    public Rectangle moveSelectedPiece(Point p){

        if(selectedPiece == null)
            return null;

        Rectangle possiblePosition = selectedPiece.checkAvailable(p);
        Rectangle window = new Rectangle(0, 0, 400, 500);

        if (possiblePosition == null || !window.contains(possiblePosition)) {
            return null;
        }

        if(checkIntersection(possiblePosition))
            return null;

        moves.add(new Move(selectedPiece.getPosition(),possiblePosition));
        win = selectedPiece.move(possiblePosition);
        setSelectedPiece(null);
        counter++;

        return possiblePosition;

    }

    public Move getLastMove(){
        return moves.getLast();
    }

    public boolean getWin(){return win;}

    public void undo(){

        Move lastMove = moves.getLast();

        setSelectedPiece(lastMove.getFinalPosition().getLocation());
        moveSelectedPiece(lastMove.getInitialPosition().getLocation());
        moves.removeLast();
        moves.removeLast();
        counter -=2;
    }

}
