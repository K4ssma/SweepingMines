package MinesweeperGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static MinesweeperGame.Variables.*;

public class Gui {

    private final MinesweeperManager manager;
    private final JFrame window;
    private final GridBagLayout gridBagLayout;
    private final GridBagConstraints constraints;
    private final JMenuBar menuBar;
    private final JMenu difficultyMenu;
    private final JPanel mineField;
    private JButton[] guiTiles;
    private final JButton restartButton;
    private final JLabel mineCountLabel;
    private final  JCheckBoxMenuItem menuItemBeginner, menuItemIntermediate, menuItemExpert;
    private final int TILEDIM = 20;
    private int currentMines;

    protected Gui(MinesweeperManager minesweeperManager){
        Debugger.info("starting Game window");

        this.manager = minesweeperManager;

        //GameWindow
        window = new JFrame("MinesweeperGame");
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
        restartButton.addActionListener(new RestartActionListener(this));

        JPanel infoPanel = new JPanel();
        mineCountLabel = new JLabel();
        JLabel bombsRemainingLabel = new JLabel("Mines Remaining");
        infoPanel.add(mineCountLabel);
        infoPanel.add(bombsRemainingLabel);

        //MenuBar
        menuBar = new JMenuBar();
        difficultyMenu = new JMenu("Difficulty");
        menuBar.add(difficultyMenu);

        menuItemBeginner = new JCheckBoxMenuItem("Beginner", false);
        menuItemBeginner.addActionListener(new DifficultyActionListener(BEGINNER, this));
        difficultyMenu.add(menuItemBeginner);

        menuItemIntermediate = new JCheckBoxMenuItem("Intermediate", false);
        menuItemIntermediate.addActionListener(new DifficultyActionListener(INTERMEDIATE, this));
        difficultyMenu.add(menuItemIntermediate);

        menuItemExpert = new JCheckBoxMenuItem("Expert", false);
        menuItemExpert.addActionListener(new DifficultyActionListener(EXPERT, this));
        difficultyMenu.add(menuItemExpert);

        switch(STANDARDDIFFICULTY.name){
            case "Beginner" ->
                menuItemBeginner.setState(true);
            case "Intermediate" ->
                menuItemIntermediate.setState(true);
            case "Expert" ->
                menuItemExpert.setState(true);
            default ->
                Debugger.warning("dont know what to do with current standard difficulty: " + STANDARDDIFFICULTY.name);
        }

        //Adding Components to the GameWindow
        window.add(BorderLayout.NORTH, menuBar);

        JPanel playGround = new JPanel();
        playGround.setLayout(new BoxLayout(playGround, BoxLayout.Y_AXIS));
        playGround.add(restartButton);
        playGround.add(infoPanel);
        playGround.add(mineField);
        window.add(BorderLayout.CENTER, playGround);

        initGUI(STANDARDDIFFICULTY);

        window.pack();
        window.setVisible(true);
        Debugger.info("Game window started");
    }

    protected void initGUI(Difficulty difficulty){
        Debugger.info("generating GUI with difficulty: " + difficulty.name);

        window.setVisible(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        //InfoBar
        currentMines = difficulty.mineNumber;
        mineCountLabel.setText(String.valueOf(currentMines));

        //MineField
        mineField.removeAll();
        guiTiles = new JButton[difficulty.dimension.width * difficulty.dimension.height];
        for(int i = 0; i < guiTiles.length; i++){
            guiTiles[i] = new JButton();
            guiTiles[i].setPreferredSize(new Dimension(TILEDIM, TILEDIM));
            guiTiles[i].addMouseListener(new TileActionListener(i, manager));
            guiTiles[i].setMargin(new Insets(0, 0, 0, 0));
            guiTiles[i].setFocusPainted(false);
            guiTiles[i].setBackground(Color.LIGHT_GRAY);
            constraints.gridx = i % difficulty.dimension.width;
            constraints.gridy = (difficulty.dimension.height - 1) - (i / difficulty.dimension.width);
            mineField.add(guiTiles[i], constraints);
        }

        currentDifficulty = difficulty;
        window.pack();
        window.setVisible(true);

        Debugger.info("GUI updated");
    }

    protected void changeDifficulty(Difficulty difficulty){
        Debugger.info("changing the difficulty from: " + currentDifficulty + " to " + difficulty.name);

        switch(currentDifficulty.name){
            case "Beginner" -> menuItemBeginner.setState(false);
            case "Intermediate" -> menuItemIntermediate.setState(false);
            case "Expert" -> menuItemExpert.setState(false);
            default -> {
                Debugger.warning("dont know how to change away from current difficulty: " + currentDifficulty.name);
                return;
            }
        }

        manager.reset();
        switch(difficulty.name){
            case "Beginner" ->
                initGUI(BEGINNER);
            case "Intermediate" ->
                initGUI(INTERMEDIATE);
            case "Expert" ->
                initGUI(EXPERT);
            default ->
                Debugger.warning("couldnt find difficulty with name: " + difficulty.name);
        }
    }

    protected void discoverTile(int x, int y, int neighbourBombCount){
        JButton tile = guiTiles[manager.coordToId(x, y)];
        if(neighbourBombCount != 0) tile.setText(Integer.toString(neighbourBombCount));
        tile.setBackground(new Color(50, 50, 50));
        tile.setForeground(new Color(230, 230, 230));
    }
    protected void setFlag(int x, int y, boolean flag){
        if(flag){
            guiTiles[manager.coordToId(x, y)].setBackground(Color.RED);
            currentMines--;
        }else{
            guiTiles[manager.coordToId(x, y)].setBackground(Color.LIGHT_GRAY);
            currentMines++;
        }
        mineCountLabel.setText(Integer.toString(currentMines));
    }

    protected void restartAction(){
        Debugger.info("restarting game");
        initGUI(currentDifficulty);
        manager.reset();
    }
}
