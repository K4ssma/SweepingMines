package Minesweeper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Gui {

    private final JFrame window;
    private final GridBagLayout gridBagLayout;
    private final GridBagConstraints constraints;
    private final JMenuBar menuBar;
    private final JMenu difficultyMenu;
    private final JPanel mineField;
    private JButton[] guiTiles;
    private final JButton restartButton;
    private final JLabel bombCountLabel;
    private final  JCheckBoxMenuItem menuItemBeginner, menuItemIntermediate, menuItemExpert;
    private final int TILEDIM = 25;
    private final Difficulty BEGINNER = new Difficulty(new Dimension(8, 8), 10);
    private final Difficulty INTERMEDIATE = new Difficulty(new Dimension(16, 16), 40);
    private final Difficulty EXPERT = new Difficulty(new Dimension(30, 16), 99);

    protected Gui(){
        Debugger.info("starting Game window");

        //GameWindow
        window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mineField = new JPanel();
        mineField.setBorder(new EmptyBorder(0, 5, 5, 5));

        //Layout
        gridBagLayout = new GridBagLayout();
        constraints = new GridBagConstraints();
        mineField.setLayout(gridBagLayout);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        //InfoBar

        restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = new JPanel();
        bombCountLabel = new JLabel();
        JLabel bombsRemainingLabel = new JLabel("Mines Remaining");
        infoPanel.add(bombCountLabel);
        infoPanel.add(bombsRemainingLabel);

        //MenuBar
        menuBar = new JMenuBar();
        difficultyMenu = new JMenu("Difficulty");
        menuBar.add(difficultyMenu);
        menuItemBeginner = new JCheckBoxMenuItem("Beginner", false);
        menuItemIntermediate = new JCheckBoxMenuItem("Intermediate", true);
        menuItemExpert = new JCheckBoxMenuItem("Expert", false);
        difficultyMenu.add(menuItemBeginner);
        difficultyMenu.add(menuItemIntermediate);
        difficultyMenu.add(menuItemExpert);

        //Adding Components to the GameWindow
        window.add(BorderLayout.NORTH, menuBar);

        JPanel playGround = new JPanel();
        playGround.setLayout(new BoxLayout(playGround, BoxLayout.Y_AXIS));
        playGround.add(restartButton);
        playGround.add(infoPanel);
        playGround.add(mineField);
        window.add(BorderLayout.CENTER, playGround);

        initGUI(INTERMEDIATE);

        window.pack();
        window.setVisible(true);
    }

    private void initGUI(Difficulty difficulty){
        window.setVisible(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        //InfoBar
        bombCountLabel.setText(String.valueOf(difficulty.bombNumber));

        //MineField
        mineField.removeAll();
        guiTiles = new JButton[difficulty.dimension.width * difficulty.dimension.height];
        for(int i = 0; i < guiTiles.length; i++){
            guiTiles[i] = new JButton();
            guiTiles[i].setPreferredSize(new Dimension(TILEDIM, TILEDIM));
            constraints.gridx = i % difficulty.dimension.width;
            constraints.gridy = (i - (i % difficulty.dimension.width)) + 3;
            mineField.add(guiTiles[i], constraints);
        }

        window.setVisible(true);
    }

    public Dimension windowSize(){
        return window.getSize();
    }
}
