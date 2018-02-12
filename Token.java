import java.awt.Dimension;


/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

/*
 * The Token class is a superclass of Character and Weapon. It stores the Tile that the movable token
 * is located on and provides method to move tile and return its coordinates.
 */

public class Token{
    //The Tile at which the token is on
    Tile currentTile;

    //constructor
    public Token(Tile currentTile){
        this.currentTile = currentTile;
        currentTile.setOccupied(true);
    }

    /**
     * @param newTile the Tile which the Token should move to
     */
    public void moveToken(Tile newTile){
        currentTile.setOccupied(false);
        currentTile = newTile;
        currentTile.setOccupied(true);
    }

    /**
     * Returns the position of the token.
     * @return a Dimension object containing the x and y coordinates of the token.
     */
    public Dimension getPosition(){
        return new Dimension(currentTile.getxCoordinate(), currentTile.getyCoordinate());
    }
}



