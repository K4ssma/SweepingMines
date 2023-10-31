package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileActionListener extends MouseAdapter implements ActionListener {
    private final int id;
    private final Gui gui;

    protected TileActionListener(int id, Gui gui){
        this.id = id;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui.triggerFieldAction(id);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isAltDown()){
            Debugger.info("RIGHT CLICK");
        }
    }
}
