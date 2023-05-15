package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

class SaveCommand extends UIController {

    String name = "";

    SaveCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        name = klotskiModel.getName();
        System.out.println(name);
        if(e.getSource() != this && name==null) {
            System.out.println("sono qua!!");
            name = klotskiUI.askGameName();
        }
        if(name == null) { // when the askName windows is closed with "X"
            return;
        }
        try{
            int tmp = klotskiModel.saveGame(name);
            if(tmp==0){
                klotskiUI.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);
            } else if (tmp==1) {
                try {
                    klotskiUI.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);

                } catch(IllegalArgumentException ex) {

                    klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
                    e.setSource(null);
                    mousePressed(e);

                } catch (Exception ex) {
                    klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch(IllegalAccessException ex){
            if(klotskiUI.showAuthenticationDialog()) {
                e.setSource(this);
                mousePressed(e);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }

}