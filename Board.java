import javax.swing.*;
import java.awt.*;

public class Board extends JComponent {
    //declare array of Tile objects
    private Tile[][] tile = new Tile[24][25];

    public Tile getTile(int i, int j) {
        return tile[i][j];
    }

    //sideLength = length of tile side
    int xSideLength = 23;
    int ySideLength = 23;

    //xDistance = x axis distance from border to tiles
    //yDistance = y axis distance from border to tiles
    int xBorder = 20;
    int yBorder = 20;

    //jframe for testing will remove when inserting into project frame
    //JFrame frame = new JFrame("Boardgame");

    public Board() {
        super();
        setSize(getXBoard(), getYBoard());
        setBoardCoordinates(tile, xBorder, yBorder, xSideLength, ySideLength); // initialise the 2D array of tiles with their coordinates
        setRoomTypes(); // set the room types, i.e. what room they're in including corridor and where there is no room.
    }

    public int getXBoard() {
        return xBorder*2 + xSideLength*24;
    }

    public int getYBoard() {
        return yBorder*2 + ySideLength*25;
    }

    //function to set the coordinates of a grid with border
    public void setBoardCoordinates(Tile[][] tile, int xBorder, int yBorder, int xSideLength, int ySideLength){
        for(int j = 0; j < 25; j++) {
            for (int i = 0; i < 24; i++) {
                int x = xBorder + i*xSideLength + xSideLength/2;
                int y = yBorder + j*ySideLength + ySideLength/2;
                tile[i][j] = new Tile(x, y);
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(0.001f)); // set the stroke for the outline of the tiles of roomType CORRIDOR

        // loop through each tile
        for(int j = 0; j < 25; j++) {
            for (int i = 0; i < 24; i++) {
                if (tile[i][j].getRoomType() == null) { // if the tile is not a tile where a player can move
                    g2.setColor(new Color(71, 124, 94)); // set the color
                } else if (tile[i][j].getRoomType() == RoomType.CORRIDOR) { // if the room is of roomType corridor
                    g2.setColor(new Color(217, 177, 103)); // set the color
                } else { // if the tile is any other roomType
                    g2.setColor(new Color(146, 131, 105)); // set the color
                }
                Rectangle rect = new Rectangle(tile[i][j].getxCoordinate()-(xSideLength/2), tile[i][j].getyCoordinate()-(ySideLength/2), xSideLength, ySideLength); // create a rectangle with the coordinates of the tile
                g2.fill(rect); // fill that rectangle on the board with the color specified above

                if (tile[i][j].getRoomType() == RoomType.CORRIDOR) { // if the room is a corridor
                    g2.setColor(new Color(171, 135, 86)); // set the color
                    g2.draw(rect); // draw the outline of the rectangle
                }
            }
        }

        g2.setColor(new Color(71, 124, 94));
        g2.fill(new Rectangle(0, 0, getXBoard(), yBorder));
        g2.fill(new Rectangle(0, 0, xBorder, getYBoard()));
        g2.fill(new Rectangle(getXBoard()-xBorder, 0, xBorder, getYBoard()));
        g2.fill(new Rectangle(0, getYBoard()-yBorder, getXBoard(), yBorder));

        g2.setColor(Color.BLACK); // set the color to black

        // display the names of each of the rooms on the JPanel
        g2.drawString("Kitchen", xBorder + 2*xSideLength, (int) (yBorder + 4.5*ySideLength));
        g2.drawString("Dining Room", (int) (xBorder + 2.5*xSideLength), (int) (yBorder + 13.5*ySideLength));
        g2.drawString("Lounge", xBorder + 3*xSideLength, yBorder + 23*ySideLength);
        g2.drawString("Ball Room", (int) (xBorder + 10.5*xSideLength), yBorder + 6*ySideLength);
        g2.drawString("Hall", xBorder + 12*xSideLength, yBorder + 22*ySideLength);
        g2.drawString("Conservatory", (int) (xBorder + 19.5*xSideLength), yBorder + 4*ySideLength);
        g2.drawString("Billard\nRoom", (int) (xBorder + 19.5*xSideLength), (int) (yBorder + 11.5*ySideLength));
        g2.drawString("Library", xBorder + 20*xSideLength, (int) (yBorder + 17.2*ySideLength));
        g2.drawString("Study", xBorder + 20*xSideLength, (int) (yBorder + 23.8*ySideLength));
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

        // assigning the rest of the tiles with roomType Corridor tiles (may not be corridor tiles, the null tiles are set manually)
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 25; j++) {
                if (tile[i][j].getRoomType() == null) {
                    tile[i][j].setRoomType(RoomType.CORRIDOR);
                }
            }
        }

        // assigning the null tiles as
        for (int i = 0; i < 24; i++) {
            if (i != 9 && i != 14) {
                tile[i][0].setRoomType(null);
            }
        }
        tile[6][1].setRoomType(null);
        tile[17][1].setRoomType(null);
        tile[17][0].setRoomType(null);
        tile[0][6].setRoomType(null);
        tile[0][8].setRoomType(null);
        tile[0][16].setRoomType(null);
        tile[0][18].setRoomType(null);
        tile[6][24].setRoomType(null);
        tile[8][24].setRoomType(null);
        tile[15][24].setRoomType(null);
        tile[17][24].setRoomType(null);
        tile[23][5].setRoomType(null);
        tile[23][7].setRoomType(null);
        tile[23][13].setRoomType(null);
        tile[23][14].setRoomType(null);
        tile[23][18].setRoomType(null);
        tile[23][20].setRoomType(null);

    }

    //tester class
    public static void main(String[] args) {
        Board board = new Board();

        JFrame frame = new JFrame();

        frame.setSize(board.getXBoard(),board.getYBoard()); // set the size of the JPanel
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(board);
        frame.setVisible(true);
    }

}
