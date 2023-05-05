package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavedGamesDialog extends JDialog {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints cs = new GridBagConstraints();

    public SavedGamesDialog(Frame parent, ActionListener listener, int numberSavedGames) {
        super(parent, "Saved games", true);
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(3,10,3,10);
        panel.setBackground(Color.white);


        for(int i = 0; i<numberSavedGames; i++)
            addEntry(i, listener);

        setSize(220, 250);
        add(BorderLayout.CENTER, new JScrollPane(panel));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void addEntry(int number, ActionListener listener){
        JButton game = new JButton("Match number: "+(number+1));
        game.setName("game");
        game.setBorder(null);
        game.setBackground(Color.white);
        game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                listener.actionPerformed(e);
            }
        });
        cs.gridx = 0;
        cs.gridy = number;
        cs.gridwidth = 2;
        panel.add(game, cs);

        JButton delete = new JButton();
        delete.setName("delete");
        delete.setIcon(new ImageIcon("./src/images/close.png"));
        delete.setBorder(null);
        delete.setBackground(Color.white);
        delete.addActionListener(listener);
        cs.gridx = 2;
        cs.gridy = number;
        cs.gridwidth = 1;
        panel.add(delete, cs);

    }



}
