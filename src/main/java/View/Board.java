package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * The Board class represents the game board panel
 */
class Board extends JPanel {

    final Block[] blocks = new Block[10];
    final JLabel displayedCounter;
    Block selectedBlock;

    /**
     * Constructs a Board panel creating Blocks and the needed JLabel
     */
    public Board() {
        setLayout(null);
        setBackground(Color.white);
        setOpaque(false);
        setBounds(10, 10, 400, 700);

        JLabel line = new JLabel();
        line.setBackground(Color.red);
        line.setOpaque(true);
        line.setBounds(100, 510, 200, 10);
        add(line);

        displayedCounter = new JLabel("Moves: 0", JLabel.CENTER);
        displayedCounter.setForeground(Color.white);
        displayedCounter.setFont(new Font("Agency FB", Font.BOLD, 25));
        displayedCounter.setBounds(10, 525, 400, 40);
        add(displayedCounter);

        for (int i = 0; i < 10; i++) {
            blocks[i] = new Block();
            add(blocks[i]);
        }
    }

    /**
     * @param moves the number of moves to display
     */
    public void setDisplayedCounter(int moves) {
        displayedCounter.setText("Moves: " + moves);
    }

    /**
     * Moves the selected block to the specified position and updates the counter.
     * @param newPos the new position for the selected block
     * @param count the updated move count
     */
    public void moveSelectedBlock(Rectangle newPos, int count) {
        selectedBlock.setBounds(newPos);
        highlightSelected(null);
        setDisplayedCounter(count);
    }

    /**
     * Highlights the selected block by applying the border in the new block and removing from the last one
     * @param selected the selected block to highlight (null for no selection)
     */
    public void highlightSelected(Block selected) {
        if (selectedBlock != null)
            selectedBlock.setBorderEnable(false);

        selectedBlock = selected;
        if (selectedBlock != null)
            selectedBlock.setBorderEnable(true);
    }

    /**
     * Selects a block based on the given point coordinates
     * @param p the point coordinates to determine the selected block
     */
    public void selectBlock(Point p) {
        if (p == null) {
            selectedBlock = null;
            return;
        }

        for (Block block : blocks) {
            if (block.getBounds().contains(p))
                selectedBlock = block;
        }
    }

    /**
     * Sets the positions of the blocks on the board
     * @param position the array of rectangle positions for each block
     */
    public void setPositions(Rectangle[] position) {
        for (int i = 0; i < 10; i++) {
            blocks[i].setBlockAppearance(position[i]);
        }
    }

    /**
     * @param listener the mouse adapter listener to add to each Block
     */
    public void addBlockListener(MouseAdapter listener) {
        for (Block block : blocks) {
            block.addMouseListener(listener);
        }
    }
}
