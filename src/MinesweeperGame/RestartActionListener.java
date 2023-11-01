package MinesweeperGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartActionListener implements ActionListener {

    private final Gui gui;

    protected RestartActionListener(Gui gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui.restartAction();
    }
}
