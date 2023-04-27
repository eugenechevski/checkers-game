package game;

import java.awt.Color;

// The class represents a cell on the board
public class Cell {
    private Color color;
    private Color selectedColor;
    private int row;
    private int col;
    private boolean isSelected;
    private Player player;

    public Cell(Player player, Color color, int row, int col) {
        this.player = player;
        this.color = color;
        this.row = row;
        this.col = col;
        this.isSelected = false;
        this.selectedColor = this.color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;

        setColor(player == null ? Color.LIGHT_GRAY : player.getColor());
        setSelectedColor(player == null ? Color.LIGHT_GRAY : player.getOppositeColor());
    }

    public boolean hasPlayer() {
        return this.player != null;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getSelectedColor() {
        return this.selectedColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void select() {
        this.isSelected = true;

    }

    public void deselect() {
        this.isSelected = false;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}