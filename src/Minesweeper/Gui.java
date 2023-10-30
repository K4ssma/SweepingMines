package Minesweeper;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame window;
    private JMenuBar menuBar;
    private JMenu difficultyMenu;
    private JButton[] guiTiles;
    private JButton restartButton;
    private JLabel bombCountLabel;
    private JCheckBoxMenuItem menuItemBeginner, menuItemIntermediate, menuItemExpert;
    private final int TILEDIM = 25;
    private final int BUFFER = 20;
    private final Difficulty BEGINNER = new Difficulty(new Dimension(8, 8), 10);
    private final Difficulty INTERMEDIATE = new Difficulty(new Dimension(16, 16), 40);
    private final Difficulty EXPERT = new Difficulty(new Dimension(30, 16), 99);
    private final Difficulty STANDARDDIFF = INTERMEDIATE;

    protected Gui(){
        Debugger.info("starting Game window");

        //GameWindow
        window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(STANDARDDIFF.dimension.width * TILEDIM + BUFFER, 430);
        JPanel playGroundPanel = new JPanel();

        //MenuBar
        menuBar = new JMenuBar();
        difficultyMenu = new JMenu("Difficulty");
        menuItemBeginner = new JCheckBoxMenuItem("Beginner", false);
        menuItemIntermediate = new JCheckBoxMenuItem("Intermediate", true);
        menuItemExpert = new JCheckBoxMenuItem("Expert", false);

        difficultyMenu.add(menuItemBeginner);
        difficultyMenu.add(menuItemIntermediate);
        difficultyMenu.add(menuItemExpert);
        menuBar.add(difficultyMenu);

        //InfoBar
        restartButton = new JButton("Restart");
        bombCountLabel = new JLabel(String.valueOf(STANDARDDIFF.bombNumber));
        JLabel bombsRemainingLabel = new JLabel("Bombs Remaining");

        JPanel infoPanel = new JPanel();
        infoPanel.add(BorderLayout.NORTH, restartButton);
        infoPanel.add(BorderLayout.CENTER, bombCountLabel);
        infoPanel.add(BorderLayout.CENTER, bombsRemainingLabel);

        //BombField
        JPanel bombFieldPanel = new JPanel(new GridLayout(STANDARDDIFF.dimension.height, STANDARDDIFF.dimension.width));
        guiTiles = new JButton[256];
        for(int i = 0; i < 256; i++){
            guiTiles[i] = new JButton();
            guiTiles[i].setPreferredSize(new Dimension(25, 25));
            bombFieldPanel.add(guiTiles[i]);
        }

        //Adding SupComponents to GameWindow
        window.add(BorderLayout.NORTH, menuBar);
        window.add(BorderLayout.CENTER, playGroundPanel);
        playGroundPanel.add(BorderLayout.NORTH, infoPanel);
        playGroundPanel.add(BorderLayout.CENTER, bombFieldPanel);

        window.setVisible(true);
    }
}
