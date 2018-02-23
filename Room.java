import java.util.ArrayList;

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Room {
    private int tokenNum; // number of tokens in the room
    private RoomType roomType;
    private Tile[] tokenPositions;
    private Tile[] doorEntrances;
    private Room secretPassage;

    Room(Board board, int x, int y, RoomType roomType, Tile[] doors) {
        tokenNum = 0;
        this.roomType = roomType;
        tokenPositions = new Tile[12]; // Tiles where the tokens are placed in the room so that the tokens are positioned in the centre of the room

        // Assign 12 tiles in the centre in the order that the players should be positioned so that the players are in the centre
        tokenPositions[0] = board.getTile(x, y);
        tokenPositions[1] = board.getTile(x+1, y);
        tokenPositions[2] = board.getTile(x, y+1);
        tokenPositions[3] = board.getTile(x+1, y+1);
        tokenPositions[4] = board.getTile(x-1, y);
        tokenPositions[5] = board.getTile(x-1, y-1);
        tokenPositions[6] = board.getTile(x-1, y-1);
        tokenPositions[7] = board.getTile(x, y-1);
        tokenPositions[8] = board.getTile(x+1, y-1);
        tokenPositions[9] = board.getTile(x+2, y-1);
        tokenPositions[10] = board.getTile(x+2, y);
        tokenPositions[11] = board.getTile(x+2, y+1);

        this.doorEntrances = doors;
        secretPassage = null;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setSecretPassage(Room secretPassage) {
        this.secretPassage = secretPassage;
    }

    public Room getSecretPassage() {
        return secretPassage;
    }

    public boolean hasSecretPasssage() {
        return secretPassage != null;
    }

    // get the room index for the rooms array in the board
    public int getRoomIndex() {
        return roomType.ordinal();
    }

    // add token to the room, i.e. have the token in the room
    public Tile addToken() {
        return tokenPositions[tokenNum++];
    }

    public int getNumDoors() {
        return doorEntrances.length;
    }

    public Tile getDoor(int doorEntranceNum) {
        return doorEntrances[doorEntranceNum];
    }

    // remove token to the room, i.e. have the token leave the room
    public boolean removeToken() {
        if (tokenNum == 0) { // if there are no tokens in the room
            return false; // unsuccessful
        }
        tokenNum--;
        return true; // successful
    }
}
