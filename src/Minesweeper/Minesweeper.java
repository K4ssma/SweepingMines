package Minesweeper;

import java.awt.*;

public class Minesweeper {

    private Gui gui;

    public void startGui(){
        gui = new Gui();
    }

    public Dimension windowSize(){
        return gui.windowSize();
    }
}