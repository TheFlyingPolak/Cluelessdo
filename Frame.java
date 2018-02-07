import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Board board;
    InfoPanel info;
    CmdPanel cmd;

    // Frame constructor
    Frame() {
        super("Cluelessdo"); // constructor of super class, parameter is the title of the frame

        board = new Board(); // board component
        info = new InfoPanel(); // info panel
        cmd = new CmdPanel(info); // command pannel

        info.setPreferredSize(new Dimension(400, board.getYBoard())); // set the preferred size of the info panel

        setSize(board.getXBoard() + 400, board.getYBoard() + 60); // set size of the Frame

        setResizable(false); // make frame non resizeable
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set the close operation to exit
        setLayout(new BorderLayout()); // set the layout to border layer

        add(board, BorderLayout.CENTER); // add the board to the center of the frame
        add(info, BorderLayout.EAST); // add the info panel to the east of the screen
        add(cmd, BorderLayout.PAGE_END); // add the command panel to the bottom of the screen
    }
}
