import javax.swing.*;
import java.awt.*;

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Board extends JComponent {
    //declare array of Tile objects
    private Tile[][] tile = new Tile[24][25];

    public Tile getTile(int i, int j) {
        return tile[i][j];
    }

    //Rooms
    private Room[] rooms;

    //sideLength = length of tile side
    public static final int X_SIDE_LENGTH = 23;
    public static final int Y_SIDE_LENGTH = 23;

    //xDistance = x axis distance from border to tiles
    //yDistance = y axis distance from border to tiles
    public static final int X_BORDER = 20;
    public static final int Y_BORDER = 20;

    // width of walls
    private int wallWidth = 6;

    //jframe for testing will remove when inserting into project frame
    //JFrame frame = new JFrame("Boardgame");

    public Board() {
        super();
        setSize(getXBoard(), getYBoard());
        initialiseTiles(); // initialise the 2D array of tiles with their coordinates
        setRoomTypes(); // set the room types, i.e. what room they're in including corridor and where there is no room.
        initialiseDoors(); // set the tiles that have doors and the direction that the door is in relation to the corridor tile beside the door
        initialiseRooms(); // initialize the rooms and assign them to the rooms array;
    }

    public int getXBoard() {
        return X_BORDER *2 + X_SIDE_LENGTH *24;
    }

    public int getYBoard() {
        return Y_BORDER *2 + Y_SIDE_LENGTH *25;
    }

    public Room getRoom(int roomIndex) {
        return rooms[roomIndex];
    }

    //function to set the coordinates of a grid with border
    public void initialiseTiles(){
        for (int x = 0; x < 24; x++) {
            for(int y = 0; y < 25; y++) {
                tile[x][y] = new Tile(x, y);
            }
        }
    }

    // initialise the rooms and assign them to the rooms array where their order is the same as the enum. The x and y parameters are the coordinates of the centre tile of the room
    public void initialiseRooms() {
        rooms = new Room[10];
        rooms[0] = new Room(this, 2, 3, RoomType.KITCHEN, new Tile[] {tile[4][7]});
        rooms[1] = new Room(this, 4, 13, RoomType.DINING, new Tile[] {tile[8][12], tile[6][16]});
        rooms[2] = new Room(this, 3, 22, RoomType.LOUNGE, new Tile[] {tile[5][18]});
        rooms[3] = new Room(this, 12, 4, RoomType.BALLROOM, new Tile[] {tile[7][5], tile[9][8], tile[14][8], tile[16][5]});
        rooms[4] = new Room(this, 12, 13, RoomType.CELLAR, new Tile[] {tile[11][17], tile[12][17], tile[13][17]});
        rooms[5] = new Room(this, 11, 14, RoomType.HALL, new Tile[] {tile[11][17], tile[12][17], tile[15][20]});
        rooms[6] = new Room(this, 20, 3, RoomType.CONSERVATORY, new Tile[] {tile[8][12]});
        rooms[7] = new Room(this, 21, 10, RoomType.BILLARD, new Tile[] {tile[18][5], tile[17][9]});
        rooms[8] = new Room(this, 21, 16, RoomType.LIBRARY, new Tile[] {tile[20][13], tile[16][16]});
        rooms[9] = new Room(this, 21, 22, RoomType.STUDY, new Tile[] {tile[17][20]});

        rooms[RoomType.KITCHEN.ordinal()].setSecretPassage(rooms[RoomType.STUDY.ordinal()]);
        rooms[RoomType.STUDY.ordinal()].setSecretPassage(rooms[RoomType.KITCHEN.ordinal()]);
        rooms[RoomType.LOUNGE.ordinal()].setSecretPassage(rooms[RoomType.CONSERVATORY.ordinal()]);
        rooms[RoomType.CONSERVATORY.ordinal()].setSecretPassage(rooms[RoomType.LOUNGE.ordinal()]);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(0.001f)); // set the stroke for the outline of the tiles of roomType CORRIDOR

        // loop through each tile
        for (int x = 0; x < 24; x++) {
            for(int y = 0; y < 25; y++) {
                if (tile[x][y].getRoomType().equals(RoomType.NO_ROOM)) { // if the tile is not a tile where a player can move
                    g2.setColor(new Color(71, 124, 94)); // set the color
                } else if (tile[x][y].getRoomType() == RoomType.CORRIDOR) { // if the room is of roomType corridor
                    g2.setColor(new Color(217, 177, 103)); // set the color
                } else { // if the tile is any other roomType
                    g2.setColor(new Color(146, 131, 105)); // set the color
                }
                Rectangle rect = new Rectangle(tile[x][y].getXCoordinate()-(X_SIDE_LENGTH /2), tile[x][y].getYCoordinate()-(Y_SIDE_LENGTH /2), X_SIDE_LENGTH, Y_SIDE_LENGTH); // create a rectangle with the coordinates of the tile
                g2.fill(rect); // fill that rectangle on the board with the color specified above

                if (tile[x][y].getRoomType() == RoomType.CORRIDOR) { // if the room is a corridor
                    g2.setColor(new Color(171, 135, 86)); // set the color
                    g2.draw(rect); // draw the outline of the rectangle
                }
            }
        }

        // draw green border around board
        g2.setColor(new Color(71, 124, 94));
        g2.fill(new Rectangle(0, 0, getXBoard(), Y_BORDER));
        g2.fill(new Rectangle(0, 0, X_BORDER, getYBoard()));
        g2.fill(new Rectangle(getXBoard()- X_BORDER, 0, X_BORDER, getYBoard()));
        g2.fill(new Rectangle(0, getYBoard()- Y_BORDER, getXBoard(), Y_BORDER));

        g2.setColor(new Color(109, 31, 36));

        drawWalls(g2);

        g2.setColor(Color.BLACK); // set the color to black

        // display the names of each of the rooms on the JPanel
        g2.drawString("Kitchen", X_BORDER + 2* X_SIDE_LENGTH, (int) (Y_BORDER + 4.5* Y_SIDE_LENGTH));
        g2.drawString("Dining Room", (int) (X_BORDER + 2.5* X_SIDE_LENGTH), (int) (Y_BORDER + 13.5* Y_SIDE_LENGTH));
        g2.drawString("Lounge", X_BORDER + 3* X_SIDE_LENGTH, Y_BORDER + 23* Y_SIDE_LENGTH);
        g2.drawString("Ball Room", (int) (X_BORDER + 10.5* X_SIDE_LENGTH), Y_BORDER + 6* Y_SIDE_LENGTH);
        g2.drawString("Hall", X_BORDER + 12* X_SIDE_LENGTH, Y_BORDER + 22* Y_SIDE_LENGTH);
        g2.drawString("Conservatory", (int) (X_BORDER + 19.5* X_SIDE_LENGTH), Y_BORDER + 4* Y_SIDE_LENGTH);
        g2.drawString("Billard\nRoom", (int) (X_BORDER + 19.5* X_SIDE_LENGTH), (int) (Y_BORDER + 11.5* Y_SIDE_LENGTH));
        g2.drawString("Library", X_BORDER + 20* X_SIDE_LENGTH, (int) (Y_BORDER + 17.2* Y_SIDE_LENGTH));
        g2.drawString("Study", X_BORDER + 20* X_SIDE_LENGTH, (int) (Y_BORDER + 23.8* Y_SIDE_LENGTH));
    }

    public void initialiseDoors() {
        tile[4][6].setDoorDirection(Direction.UP); // Kitchen door

        tile[7][12].setDoorDirection(Direction.LEFT); // dining room door
        tile[6][15].setDoorDirection(Direction.UP); // dining room door

        tile[5][19].setDoorDirection(Direction.DOWN); // lounge door

        tile[8][5].setDoorDirection(Direction.RIGHT); // ballroom door
        tile[9][7].setDoorDirection(Direction.UP); // ballroom door
        tile[14][7].setDoorDirection(Direction.UP); // ballroom door
        tile[15][5].setDoorDirection(Direction.LEFT); // ballroom door


        tile[11][16].setDoorDirection(Direction.UP); // Cellar Door
        tile[12][16].setDoorDirection(Direction.UP); // Cellar Door
        tile[13][16].setDoorDirection(Direction.UP); // Cellar Door

        tile[11][18].setDoorDirection(Direction.DOWN); // Hall Door
        tile[12][18].setDoorDirection(Direction.DOWN); // Hall Door

        tile[18][4].setDoorDirection(Direction.UP); // Conservatory door

        tile[18][9].setDoorDirection(Direction.RIGHT); // billard room door
        tile[22][12].setDoorDirection(Direction.UP); // billard room door

        tile[20][14].setDoorDirection(Direction.DOWN); // library door
        tile[17][16].setDoorDirection(Direction.RIGHT); // library door

        tile[17][21].setDoorDirection(Direction.DOWN); // study door
    }

    private void drawWalls(Graphics2D g2) {
        // filling the walls for the kitchen
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, 6* Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, 5* Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); // horizontal
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, 3* X_SIDE_LENGTH + wallWidth/2,  wallWidth)); // horizontal
        g2.fill(new Rectangle(X_BORDER + 5* X_SIDE_LENGTH, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2,  wallWidth)); // horizontal

        // filling the walls for the Dining Room
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 9* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 5* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 9* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 2* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 13* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 9* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 5* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH - wallWidth/2, 3* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 16* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH, Y_BORDER + 16* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for the lounge
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH, wallWidth, 6* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH, wallWidth, 5* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for the Ball Room
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH - wallWidth/2, Y_BORDER, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH, wallWidth)); //horizontal

        // filling the walls for the Cellar
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for the Hall
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 21* Y_SIDE_LENGTH, wallWidth, 4* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 13* X_SIDE_LENGTH, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal

        // filling the walls for the Conservatory
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH, wallWidth, 4* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 19* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 5* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 5* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH, wallWidth, 4* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 5* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 19* X_SIDE_LENGTH, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for the Billard Room
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 13* Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 13* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH, wallWidth, 5* Y_SIDE_LENGTH)); // vertical

        // filling the walls for the Library
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 14* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 14* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 17* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 14* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 21* X_SIDE_LENGTH, Y_BORDER + 14* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal

        // filling the walls for the Study
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 21* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); //vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 21* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 4* Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH, Y_BORDER + 21* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); // horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2,  wallWidth)); // horizontal

        // Setting the walls for the edges of the corridor
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 16* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 13* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 20* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 20* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH, Y_BORDER - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH - wallWidth/2, Y_BORDER - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal


    }

    public void setRoomTypes() {
        // 2-D array of the roomtypes for each tile corresponding to the tiles 2-D array
        RoomType[][] roomTypes = {
            {RoomType.NO_ROOM, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE},
            {RoomType.NO_ROOM, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE},
            {RoomType.NO_ROOM, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE},
            {RoomType.NO_ROOM, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE},
            {RoomType.NO_ROOM, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE},
            {RoomType.NO_ROOM, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE},
            {RoomType.NO_ROOM, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.LOUNGE, RoomType.NO_ROOM},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.DINING, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.NO_ROOM},
            {RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL},
            {RoomType.NO_ROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL},
            {RoomType.NO_ROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL},
            {RoomType.NO_ROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL},
            {RoomType.NO_ROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL},
            {RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL, RoomType.HALL},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.BALLROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.NO_ROOM},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR},
            {RoomType.NO_ROOM, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.NO_ROOM},
            {RoomType.NO_ROOM, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.CORRIDOR, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY},
            {RoomType.NO_ROOM, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.CORRIDOR, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY},
            {RoomType.NO_ROOM, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.CORRIDOR, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY},
            {RoomType.NO_ROOM, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.CORRIDOR, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY},
            {RoomType.NO_ROOM, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.CORRIDOR, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY},
            {RoomType.NO_ROOM, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.CONSERVATORY, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.BILLARD, RoomType.NO_ROOM, RoomType.NO_ROOM, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.LIBRARY, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY, RoomType.STUDY},
        };

        // loop through 2-D board array setting the roomType for each tile
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 25; j++) {
                tile[i][j].setRoomType(roomTypes[i][j]);
            }
        }
    }
}
