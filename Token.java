package com.cluelessdo;

import java.awt.Dimension;

/*
 * The Token class is a superclass of Character and Weapon. It stores the coordinates of the movable token
 * and provides method to change and return its coordinates.
 *
 * @author Jakub Gajewski
 */

public class Token{
    /** Used to store the x and y coordinates of the token */
    private int xPosition;
    private int yPosition;

    public Token(int x, int y){
        xPosition = x;
        yPosition = y;
    }

    /**
     * Changes the x and y coordinates of the token by the specified x and y difference
     * @param dx the value by which the x coordinate should be changed
     * @param dy the value by which the y coordinate should be changed
     */
    public void moveToken(int dx, int dy){
        xPosition += dx;
        yPosition += dy;
    }

    /**
     * Returns the position of the token.
     * @return a Dimension object containing the x and y coordinates of the token.
     */
    public Dimension getPosition(){
        return new Dimension(xPosition, yPosition);
    }
}



