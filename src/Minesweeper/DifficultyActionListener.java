package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyActionListener implements ActionListener {

    private final Difficulty difficulty;
    private final Gui gui;

    protected DifficultyActionListener(Difficulty difficulty, Gui gui){
        this.difficulty = difficulty;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui.changeDifficulty(difficulty);
    }
}
