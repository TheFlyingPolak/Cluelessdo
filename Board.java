import javax.swing.*;
import java.awt.*;

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Board extends JComponent {
    //declare array of Tile objects
    private Tile[][] tile = new Tile[24][25];

    public Tile getTile(int i, int j) {
        return tile[i][j];
    }

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
    }

    public int getXBoard() {
        return X_BORDER *2 + X_SIDE_LENGTH *24;
    }

    public int getYBoard() {
        return Y_BORDER *2 + Y_SIDE_LENGTH *25;
    }

    //function to set the coordinates of a grid with border
    public void initialiseTiles(){
        for (int x = 0; x < 24; x++) {
            for(int y = 0; y < 25; y++) {
                tile[x][y] = new Tile(x, y);
            }
        }
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
                Rectangle rect = new Rectangle(tile[x][y].getxCoordinate()-(X_SIDE_LENGTH /2), tile[x][y].getyCoordinate()-(Y_SIDE_LENGTH /2), X_SIDE_LENGTH, Y_SIDE_LENGTH); // create a rectangle with the coordinates of the tile
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

    // assign a roomType for each tile
    public void setRoomTypes() {
        // assigning the kitchen tiles with their type
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                tile[i][j].setRoomType(RoomType.KITCHEN);
            }
        }
        for (int i = 1; i < 6; i++) {
            tile[i][6].setRoomType(RoomType.KITCHEN);
        }

        // assigning the Dining room tiles with their type
        for (int i = 0; i < 5; i++) {
            tile[i][9].setRoomType(RoomType.DINING);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 10; j < 16; j++) {
                tile[i][j].setRoomType(RoomType.DINING);
            }
        }

        // assigning the Lounge tiles with their type
        for (int i = 0; i < 7; i++) {
            for (int j = 19; j < 25; j++) {
                tile[i][j].setRoomType(RoomType.LOUNGE);
            }
        }
        for (int i = 0; i < 6; i++) {
            tile[i][24].setRoomType(RoomType.LOUNGE);
        }

        // assigning the Ball room tiles with their type
        for (int i = 10; i < 14; i++) {
            tile[i][1].setRoomType(RoomType.BALLROOM);
        }
        for (int i = 8; i < 16; i++) {
            for (int j = 2; j < 8; j++) {
                tile[i][j].setRoomType(RoomType.BALLROOM);
            }
        }

        // assigning the Cellar tiles with their type
        for (int i = 10; i < 15; i++) {
            for (int j = 10; j < 17; j++) {
                tile[i][j].setRoomType(RoomType.CELLAR);
            }
        }

        // assigning the Hall tiles with their type
        for (int i = 9; i < 15; i++) {
            for (int j = 18; j < 25; j++) {
                tile[i][j].setRoomType(RoomType.HALL);
            }
        }

        // assigning the Conservatory tiles with their type
        for (int i = 18; i < 24; i++) {
            for (int j = 1; j < 5; j++) {
                tile[i][j].setRoomType(RoomType.CONSERVATORY);
            }
        }
        for (int i = 19; i < 24; i++) {
            tile[i][5].setRoomType(RoomType.CONSERVATORY);
        }

        // assigning the Billard tiles with their type
        for (int i = 18; i < 24; i++) {
            for (int j = 8; j < 13; j++) {
                tile[i][j].setRoomType(RoomType.BILLARD);
            }
        }

        // assigning the Library tiles with their type
        for (int i = 18; i < 24; i++) {
            for (int j = 14; j < 19; j++) {
                tile[i][j].setRoomType(RoomType.LIBRARY);
            }
        }
        for (int j = 15; j < 18; j++) {
            tile[17][j].setRoomType(RoomType.LIBRARY);
        }

        // assigning the Study tiles with their type
        for (int i = 17; i < 24; i++) {
            for (int j = 21; j < 24; j++) {
                tile[i][j].setRoomType(RoomType.STUDY);
            }
        }
        for (int i = 18; i < 24; i++) {
            tile[i][24].setRoomType(RoomType.STUDY);
        }

        // assigning the rest of the tiles with roomType Corridor tiles (may not be corridor tiles, the green tiles are set manually)
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 25; j++) {
                if (tile[i][j].getRoomType() == null) {
                    tile[i][j].setRoomType(RoomType.CORRIDOR);
                }
            }
        }

        // assigning the tiles that are not part of the board i.e the green as
        for (int i = 0; i < 24; i++) {
            if (i != 9 && i != 14) {
                tile[i][0].setRoomType(RoomType.NO_ROOM);
            }
        }
        tile[6][1].setRoomType(RoomType.NO_ROOM);
        tile[17][1].setRoomType(RoomType.NO_ROOM);
        tile[17][0].setRoomType(RoomType.NO_ROOM);
        tile[0][6].setRoomType(RoomType.NO_ROOM);
        tile[0][8].setRoomType(RoomType.NO_ROOM);
        tile[0][16].setRoomType(RoomType.NO_ROOM);
        tile[0][18].setRoomType(RoomType.NO_ROOM);
        tile[6][24].setRoomType(RoomType.NO_ROOM);
        tile[8][24].setRoomType(RoomType.NO_ROOM);
        tile[15][24].setRoomType(RoomType.NO_ROOM);
        tile[17][24].setRoomType(RoomType.NO_ROOM);
        tile[23][5].setRoomType(RoomType.NO_ROOM);
        tile[23][7].setRoomType(RoomType.NO_ROOM);
        tile[23][13].setRoomType(RoomType.NO_ROOM);
        tile[23][14].setRoomType(RoomType.NO_ROOM);
        tile[23][18].setRoomType(RoomType.NO_ROOM);
        tile[23][20].setRoomType(RoomType.NO_ROOM);
    }
}
