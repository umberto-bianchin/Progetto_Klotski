package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;

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

    public void addListener(MouseAdapter listener) {
        this.addMouseListener(listener);
    }

    public void setPositions(Rectangle[] position) throws IOException {

        for (int i = 0; i < 10; i++) {
            blocks[i].setBlockAppearance(position[i]);
        }
    }

    public Block[] getBlocksRepresentation() {
        return blocks;
    }
}

