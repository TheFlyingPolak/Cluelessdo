
/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Tile {
    //the centre coordinates of each tile
    private int tileX;
    private int tileY;
    private RoomType roomType;
    private Direction doorDirection;

    //boolean variable that shows whether or not a tile
    private boolean occupied;

    //Tile constructor
    public Tile(int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
        this.occupied= false;
        this.doorDirection = null;
    }

    public Direction getDoorDirection() {
        return doorDirection;
    }

    public void setDoorDirection(Direction doorDirection) {
        this.doorDirection = doorDirection;
    }

    //set roomType
    public void setRoomType(RoomType room) {
        this.roomType = room;
    }

    //get roomType
    public RoomType getRoomType() {
        return roomType;
    }

    // accessor methods to get the location of the tile in the 2-D array
    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    // get the coordinates of the tile methods
    public int getXCoordinate() {
        return Board.X_BORDER + tileX * Board.X_SIDE_LENGTH + Board.X_SIDE_LENGTH/2;
    }

    public int getYCoordinate() {
        return Board.Y_BORDER + tileY * Board.Y_SIDE_LENGTH + Board.Y_SIDE_LENGTH/2;
    }

    public boolean isOccupied() {
        return occupied;
    }

    // function to check whether there's a wall below the tile
    boolean hasWallDown(Tile sideTile) {
        return ((tileY != 25-1 && !getRoomType().equals(sideTile.getRoomType())) || (tileY == 25-1 && !roomType.equals(RoomType.NO_ROOM))) && doorDirection == null;
    }

    // function to check whether there's a wall above the tile
    boolean hasWallUp(Tile sideTile) {
        return ((!getRoomType().equals(sideTile.getRoomType())) || (tileY == 0 && (tileX == 9 || tileX == 14))) && doorDirection == null;
    }

    // function to check whether there's a wall to the left of tile
    boolean hasWallLeft(Tile sideTile) {
        return ((tileX != 0 && !getRoomType().equals(sideTile.getRoomType())) || (tileX == 0 && !roomType.equals(RoomType.NO_ROOM))) && doorDirection == null;
    }

    // function to check whether there's a wall to the right of the tile
    boolean hasWallRight(Tile sideTile) {
        return ((tileX != 24-1 && !getRoomType().equals(sideTile.getRoomType())) || (tileX == 24-1 && !roomType.equals(RoomType.NO_ROOM))) && doorDirection == null;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
