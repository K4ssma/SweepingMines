package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileActionListener implements ActionListener {
    private final int id;
    private final Gui gui;

    protected TileActionListener(int id, Gui gui){
        this.id = id;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui.triggerField(id);
    }
}
