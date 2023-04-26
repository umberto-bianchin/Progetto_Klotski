import java.awt.*;
import java.util.LinkedList;

public class State {

    LinkedList<Move> moves;
    Rectangle[] initial_config;
    Rectangle[] final_config;

    // nuova partita => configurazione iniziale
    public State(Rectangle[] config){
        initial_config = config;
        moves = new LinkedList<Move>();
        final_config = new Rectangle[10];
    }

    //partita iniziata => mosse, configurazione iniziale (per il reset) e finale
    public State(LinkedList<Move> saved_moves, Rectangle[] starting_config, Rectangle[] saved_config){
        moves = saved_moves;
        initial_config = starting_config;
        final_config = saved_config;
    }




}
