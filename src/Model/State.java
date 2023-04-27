package Model;

import java.awt.*;
import java.util.LinkedList;

public class State {

    LinkedList<Move> moves;
    Rectangle[] initial_config;
    Piece[] current_config;
    private Piece selectedPiece;
    private int counter = 0;


    // nuova partita => configurazione iniziale
    public State(Rectangle[] config){
        initial_config = config;
        moves = new LinkedList<>();

        current_config = new Piece[10];
        for(int i=0; i<10; i++){
            current_config[i] = new Piece(config[i]);
        }

    }

    //partita iniziata => mosse, configurazione iniziale (per il reset) e finale
    public State(LinkedList<Move> saved_moves, Rectangle[] starting_config, Rectangle[] saved_config){
        moves = saved_moves;
        initial_config = starting_config;

        current_config = new Piece[10];
        for(int i=0; i<10; i++){
            current_config[i] = new Piece(saved_config[i]);
        }
    }

    public Rectangle[] getInitial_config(){
        return initial_config;
    }

    public Piece[] getCurrent_config(){
        return current_config;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void incrementCounter(){
        counter++;
    }

    public int getCounter(){
        return counter;
    }

    public Piece getSelectedPiece(){
        return selectedPiece;
    }

}
