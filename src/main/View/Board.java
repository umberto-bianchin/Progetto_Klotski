package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

class Board extends JPanel {

    final Block[] blocks = new Block[10];
    final JLabel displayedCounter;
    Block selectedBlock;


    public Board(int counter) {
        setLayout(null);
        setBackground(Color.white);

        JLabel line = new JLabel();
        line.setBackground(Color.red);
        line.setOpaque(true);
        line.setBounds(100, 505, 200, 10);
        line.setSize(200, 10);
        add(line);

        displayedCounter = new JLabel("Moves: " + counter);
        displayedCounter.setHorizontalAlignment(JLabel.CENTER);
        displayedCounter.setBackground(Color.white);
        displayedCounter.setFont(new Font("Serif", Font.BOLD, 25));
        displayedCounter.setBounds(10, 525, 400, 40);
        displayedCounter.setSize(400, 40);
        add(displayedCounter);

        for (int i = 0; i < 10; i++) {
            blocks[i] = new Block();
            add(blocks[i]);
        }
    }

    public void setDisplayedCounter(int moves) {
        displayedCounter.setText("Moves: " + moves);
    }

    public void moveSelectedBlock(Rectangle newPos, int count) {
        selectedBlock.setBounds(newPos);
        highlightSelected(null);
        setDisplayedCounter(count);
    }

    public void highlightSelected(Block selected) {
        if (selectedBlock != null) {
            selectedBlock.setBorder(false);
        }
        selectedBlock = selected;
        if (selectedBlock != null)
            selectedBlock.setBorder(true);
    }

    public void selectBlock(Point p){
        if(p==null){
            selectedBlock = null;
            return;
        }

        for(Block block : blocks) {
            if(block.getBounds().contains(p))
                selectedBlock = block;
        }

    }

    public void setPositions(Rectangle[] position) {

        for (int i = 0; i < 10; i++) {
            blocks[i].setBlockAppearance(position[i]);
        }
    }

    public void addListener(MouseAdapter listener) {
        addMouseListener(listener);
    }

    public void addBlockListener(MouseAdapter listener){
        for(Block block : blocks){
            block.addListener(listener);
        }
    }

    public void makeMove(Rectangle[] move, int count){
        selectBlock(move[0].getLocation());
        moveSelectedBlock(move[1], count);
    }
}

