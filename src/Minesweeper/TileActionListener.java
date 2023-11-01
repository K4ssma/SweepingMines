package Minesweeper;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileActionListener extends MouseAdapter {
    private final int id;
    private final MinesweeperManager manager;
    private boolean isHovered;

    protected TileActionListener(int id, MinesweeperManager manager){
        this.id = id;
        this.manager = manager;
        this.isHovered = false;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(!isHovered) return;

        Dimension coord = manager.idToCoord(id);
        if(e.getButton() == MouseEvent.BUTTON1){
            manager.clickTile(coord.width, coord.height);
        }else if(e.getButton() == MouseEvent.BUTTON3){
            manager.rightClickTile(coord.width, coord.height);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e){
        isHovered = true;
    }
    @Override
    public void mouseExited(MouseEvent e){
        isHovered = false;
    }

}
