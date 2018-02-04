import javax.swing.*;
import java.awt.*;

public class Board {

    public Board(){
        //jframe for testing will remove when inserting into project frame
        JFrame frame = new JFrame("Boardgame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(1000,1200);

        //insert image of board onto JPanel
        JPanel board = new JPanel();
        ImageIcon icon = new ImageIcon("cluedo.jpg");
        JMenuItem iconMenuItem = new JMenuItem(icon);
        board.add(iconMenuItem);
        frame.add(iconMenuItem);

        //sideLength = length of tile side
        int xSideLength = 23;
        int ySideLength = 22;

        //xDistance = x axis distance from border to tiles
        //yDistance = y axis distance from border to tiles
        int xBorder = 43;
        int yBorder = 20;

        //declare array of Tile objects
        Tile[][] tile = new Tile[24][25];

        setBoardCoordinates(tile, xBorder, yBorder, xSideLength, ySideLength);

        frame.setVisible(true);
    }

    //function to set the coordinates of a grid with border
    public static void setBoardCoordinates(Tile[][] tile, int xBorder, int yBorder, int xSideLength, int ySideLength){
        int i = 1;
        int j = 1;

        for(int a=0; a<24; a++) {
            for (int b=0; b<25; b++) {
                tile[a][b].setxCoordinate(xBorder + ((i * xSideLength) / 2));
                tile[a][b].setyCoordinate(yBorder + ((j * ySideLength) / 2));
                i = i+2;
            }
            j=j+2;
        }

    }

}
