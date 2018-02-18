/*
 * The Character class is a subclass of Token and specifies the name of the character token.
 *
 * @author Jakub Gajewski
 */

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

import java.awt.*;

public class Character extends Token{
    private CharacterNames name;

    public Character(Tile currentTile, CharacterNames name){
        super(currentTile);
        this.name = name;
    }

    /*
        checks to see if the move is valid, if valid moves the player
        @return returns false if move is illegal, true if move has been made successfully
     */
    boolean moveToken(Direction dir, Board board) {
        Tile currTile = getCurrentTile();
        int x = currTile.getTileX(); // player X position on board
        int y = currTile.getTileY(); // player Y position on board
        Tile nextTile = null;
        switch(dir) {
            case UP: {
                if (y != 0) { // if player is on the top edge of the board
                    nextTile = board.getTile(x, y-1); // tile player wants to move to
                    if (!currTile.hasWallUp(nextTile)) {
                        return false; // player cant move to that tile
                    } else if (nextTile.getDoorDirection() == Direction.UP) {
                        nextTile = board.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
            case DOWN: {
                if (y != 25-1) { // if player is on the bottom edge of the board
                    nextTile = board.getTile(x, y+1);
                    if (currTile.hasWallDown(nextTile)) {
                        return false; // player cant move to that tile
                    } else if (nextTile.getDoorDirection() == Direction.DOWN) {
                        nextTile = board.getRoom(currTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
            case LEFT: {
                if (x != 0) {// if player is on the left edge of the board
                    nextTile = board.getTile(x-1, y);
                    if (currTile.hasWallUp(nextTile)) {
                        return false; // player cant move to that tile
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
            case RIGHT: {// if player is on the right edge of the board
                if (x != 24-1) {
                    nextTile = board.getTile(x + 1, y);
                    if (currTile.hasWallUp(nextTile)) {
                        return false; // player cant move to that tile
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
        }
        if (nextTile != null) { // if nextTile has not been set
            super.moveToken(nextTile);
            return true; // move successful
        } else {
            return false; // nexttile has not been set due t
        }
    }

    public CharacterNames getName(){
        return name;
    }
}