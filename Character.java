/*
 * The Character class is a subclass of Token and specifies the name of the character token.
 *
 * @author Jakub Gajewski
 */

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Character extends Token{
    private CharacterNames name;
    private RoomType roomLastOccupied;

    public Character(Tile currentTile, CharacterNames name){
        super(currentTile);
        this.name = name;
        roomLastOccupied = null;
    }

    /*
        checks to see if the move is valid, if valid moves the player
        @return returns false if move is illegal, true if move has been made successfully
     */
    boolean moveToken(Direction dir, Map map) {
        Tile currTile = getCurrentTile();
        int x = currTile.getTileX(); // player X position on board
        int y = currTile.getTileY(); // player Y position on board
        Tile nextTile = null;
        switch(dir) {
            case UP: {
                if (y != 0) { // if player is on the top edge of the board
                    nextTile = map.getTile(x, y-1); // tile player wants to move to
                    if (currTile.hasWallUp(map) || nextTile.isOccupied()) {
                        return false; // player cant move to that tile
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return false; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.UP) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
            case DOWN: {
                if (y != 25-1) { // if player is on the bottom edge of the board
                    nextTile = map.getTile(x, y+1);
                    if (currTile.hasWallDown(map) || nextTile.isOccupied()) {
                        return false; // player cant move to that tile
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return false; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.DOWN) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
            case LEFT: {
                if (x != 0) {// if player is on the left edge of the board
                    nextTile = map.getTile(x-1, y);
                    if (currTile.hasWallLeft(map) || nextTile.isOccupied()) {
                        return false; // player cant move to that tile
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return false; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.LEFT) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return false; // player cant move to that tile
                }
                break;
            }
            case RIGHT: {// if player is on the right edge of the board
                if (x != 24-1) {
                    nextTile = map.getTile(x + 1, y);
                    if (currTile.hasWallRight(map) || nextTile.isOccupied()) {
                        return false; // player cant move to that tile
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return false; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.RIGHT) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
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
            return false; // nextTile has not been set due t
        }
    }

    public boolean moveOutOfRoom(Tile nextTile) {
        if (nextTile.isOccupied()) {
            return false;
        }
        super.moveToken(nextTile);
        return true;
    }

    public CharacterNames getName(){
        return name;
    }

    public RoomType getRoomLastOccupied(){
        return roomLastOccupied;
    }

    public void setRoomLastOccupied(RoomType room){
        roomLastOccupied = room;
    }
}