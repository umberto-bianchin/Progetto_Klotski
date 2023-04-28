package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;

public class Board extends JPanel {

    private final PieceRepresentation[] piecesRepresentations = new PieceRepresentation[10];
    private PieceRepresentation selectedPieceRepresentation;
    private final JLabel displayedCounter;

    public Board() {
        setLayout(null);
        setBackground(Color.white);

        JLabel line = new JLabel();
        line.setBackground(Color.red);
        line.setOpaque(true);
        line.setBounds(100,505,200,10);
        line.setSize(200, 10);
        add(line);

        displayedCounter = new JLabel();
        displayedCounter.setHorizontalAlignment(JLabel.CENTER);
        displayedCounter.setBackground(Color.white);
        displayedCounter.setFont(new Font("Serif", Font.BOLD, 25));
        displayedCounter.setBounds(10,525,400,40);
        displayedCounter.setSize(400, 40);
        add(displayedCounter);

    }

    public void setDisplayedCounter(int step){
        displayedCounter.setText("Step "+ step);
    }

    public PieceRepresentation getSelectedPieceRepresentation(){
        return selectedPieceRepresentation;
    }

    public void selectPiece(PieceRepresentation selected) {
        if (selectedPieceRepresentation != null) {
            selectedPieceRepresentation.setBorder(false);
        }
        selectedPieceRepresentation = selected;
        selectedPieceRepresentation.setBorder(true);
    }

    public void addListener(MouseAdapter listener){
        this.addMouseListener(listener);
    }

    public void setPiecesRepresentation(Rectangle[] initialPosition) throws IOException {



        for(int i=0; i<10; i++) {
            piecesRepresentations[i] = new PieceRepresentation(initialPosition[i]);
            add(piecesRepresentations[i]);
        }
        repaint();
    }

    public void removePiecesRepresentation(Rectangle[] initialPosition){
        for(int i=0; i<10; i++) {
            piecesRepresentations[i].setBounds(initialPosition[i]);
        }
        repaint();
    }
    public PieceRepresentation[] getPiecesRepresentation() {
        return piecesRepresentations;
    }
}