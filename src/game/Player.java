package game;

import java.awt.Color;

// This class is used to store the player's instance
public class Player {
    private String name;
    private Color color;
    private Color oppositeColor;
    private boolean isKing;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.oppositeColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        this.isKing = false;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Color getOppositeColor() {
        return oppositeColor;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing() {
        this.isKing = true;
    }
}