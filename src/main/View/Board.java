package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

class Board extends JPanel {

    private final Block[] blocks = new Block[10];
    private final JLabel displayedCounter;
    private Block selectedBlock;

    public Board() {
        setLayout(null);
        setBackground(Color.white);

        JLabel line = new JLabel();
        line.setBackground(Color.red);
        line.setOpaque(true);
        line.setBounds(100, 505, 200, 10);
        line.setSize(200, 10);
        add(line);

        displayedCounter = new JLabel();
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

    public void setDisplayedCounter(int step) {
        displayedCounter.setText("Step " + step);
    }

    public void moveSelectedBlock(Rectangle newPos) {
        selectedBlock.setBounds(newPos);
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

    public void addListener(MouseAdapter listener) {
        addMouseListener(listener);
    }

    public void setPositions(Rectangle[] position) {

        for (int i = 0; i < 10; i++) {
            blocks[i].setBlockAppearance(position[i]);
        }
    }

    public void addBlockListener(MouseAdapter listener){
        for(Block block : blocks){
            block.addListener(listener);
        }
    }
}

