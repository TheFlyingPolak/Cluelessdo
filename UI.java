import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class UI extends JFrame {
    private JPanel boardPanel;
    private JLayeredPane layers;
    private Board board;
    private InfoPanel info;
    private CmdPanel cmd;

    // UI constructor
    UI() throws IOException {
        super("Cluelessdo"); // constructor of super class, parameter is the title of the frame

        boardPanel = new JPanel(new BorderLayout());  // Panel which contains the JLayeredPane
        layers = new JLayeredPane();    // Layered container for board and token drawing panel
        board = new Board(); // board component
        info = new InfoPanel(); // info panel
        cmd = new CmdPanel(); // command pannel

        info.setPreferredSize(new Dimension(400, board.getYBoard())); // set the preferred size of the info panel

        boardPanel.setSize(board.getXBoard(), board.getYBoard());
        boardPanel.add(layers);
        board.setBounds(0, 0, board.getXBoard(), board.getYBoard());

        layers.add(board, Integer.valueOf(1));

        setSize(board.getXBoard() + 400, board.getYBoard() + 60); // set size of the UI

        setResizable(false); // make frame non resizeable
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set the close operation to exit
        setLayout(new BorderLayout()); // set the layout to border layout

        add(boardPanel, BorderLayout.CENTER); // add the board to the center of the frame
        add(info, BorderLayout.EAST); // add the info panel to the east of the screen
        add(cmd, BorderLayout.PAGE_END); // add the command panel to the bottom of the screen
    }

    public Board getBoard() {
        return board;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    public InfoPanel getInfo() {
        return info;
    }

    public CmdPanel getCmd() {
        return cmd;
    }
}
