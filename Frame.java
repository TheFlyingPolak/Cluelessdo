import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Frame extends JFrame {
    JPanel boardPanel;
    JLayeredPane layers;
    TokenController tokenPanel;
    Board board;
    InfoPanel info;
    CmdPanel cmd;

    // Frame constructor
    Frame() throws IOException {
        super("Cluelessdo"); // constructor of super class, parameter is the title of the frame

        boardPanel = new JPanel(new BorderLayout());  // Panel which contains the JLayeredPane
        layers = new JLayeredPane();    // Layered container for board and token drawing panel
        tokenPanel = new TokenController();     // Token drawing panel
        board = new Board(); // board component
        info = new InfoPanel(); // info panel
        cmd = new CmdPanel(info); // command pannel

        info.setPreferredSize(new Dimension(400, board.getYBoard())); // set the preferred size of the info panel

        boardPanel.setSize(board.getXBoard(), board.getYBoard());
        boardPanel.add(layers);
        board.setBounds(0, 0, board.getXBoard(), board.getYBoard());
        tokenPanel.setBounds(0, 0, board.getXBoard(), board.getYBoard());
        tokenPanel.setOpaque(false);

        layers.add(board, new Integer(1));
        layers.add(tokenPanel, new Integer(2));

        setSize(board.getXBoard() + 400, board.getYBoard() + 60); // set size of the Frame

        setResizable(false); // make frame non resizeable
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set the close operation to exit
        setLayout(new BorderLayout()); // set the layout to border layout

        add(boardPanel, BorderLayout.CENTER); // add the board to the center of the frame
        add(info, BorderLayout.EAST); // add the info panel to the east of the screen
        add(cmd, BorderLayout.PAGE_END); // add the command panel to the bottom of the screen
    }

    /**
     * Method to test the game screen by placing tokens on the board and moving them.
     */
    public void test() {
        initialiseTokens();

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tokenPanel.getWeaponToken(0).moveToken(board.getTile(1, 2));
            tokenPanel.getWeaponToken(1).moveToken(board.getTile(5, 6));
            tokenPanel.getWeaponToken(2).moveToken(board.getTile(8, 9));
            tokenPanel.getWeaponToken(3).moveToken(board.getTile(16, 15));
            tokenPanel.getWeaponToken(4).moveToken(board.getTile(11, 15));
            tokenPanel.getWeaponToken(5).moveToken(board.getTile(20, 21));

            tokenPanel.getPlayerToken(0).moveToken(board.getTile(14, 1));
            tokenPanel.getPlayerToken(1).moveToken(board.getTile(22, 6));
            tokenPanel.getPlayerToken(2).moveToken(board.getTile(9, 1));
            tokenPanel.getPlayerToken(3).moveToken(board.getTile(1, 17));
            tokenPanel.getPlayerToken(4).moveToken(board.getTile(22, 19));
            tokenPanel.getPlayerToken(5).moveToken(board.getTile(7, 23));

            tokenPanel.repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tokenPanel.getWeaponToken(0).moveToken(board.getTile(2, 2));
            tokenPanel.getWeaponToken(1).moveToken(board.getTile(6, 6));
            tokenPanel.getWeaponToken(2).moveToken(board.getTile(9, 9));
            tokenPanel.getWeaponToken(3).moveToken(board.getTile(16, 16));
            tokenPanel.getWeaponToken(4).moveToken(board.getTile(11, 16));
            tokenPanel.getWeaponToken(5).moveToken(board.getTile(20, 20));

            tokenPanel.getPlayerToken(0).moveToken(board.getTile(15, 1));
            tokenPanel.getPlayerToken(1).moveToken(board.getTile(21, 6));
            tokenPanel.getPlayerToken(2).moveToken(board.getTile(8, 1));
            tokenPanel.getPlayerToken(3).moveToken(board.getTile(2, 17));
            tokenPanel.getPlayerToken(4).moveToken(board.getTile(21, 19));
            tokenPanel.getPlayerToken(5).moveToken(board.getTile(7, 22));

            tokenPanel.repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tokenPanel.getWeaponToken(0).moveToken(board.getTile(2, 1));
            tokenPanel.getWeaponToken(1).moveToken(board.getTile(6, 5));
            tokenPanel.getWeaponToken(2).moveToken(board.getTile(9, 8));
            tokenPanel.getWeaponToken(3).moveToken(board.getTile(15, 16));
            tokenPanel.getWeaponToken(4).moveToken(board.getTile(10, 16));
            tokenPanel.getWeaponToken(5).moveToken(board.getTile(20, 21));

            tokenPanel.getPlayerToken(0).moveToken(board.getTile(14, 1));
            tokenPanel.getPlayerToken(1).moveToken(board.getTile(22, 6));
            tokenPanel.getPlayerToken(2).moveToken(board.getTile(9, 1));
            tokenPanel.getPlayerToken(3).moveToken(board.getTile(1, 17));
            tokenPanel.getPlayerToken(4).moveToken(board.getTile(22, 19));
            tokenPanel.getPlayerToken(5).moveToken(board.getTile(7, 23));

            tokenPanel.repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tokenPanel.getWeaponToken(0).moveToken(board.getTile(1, 1));
            tokenPanel.getWeaponToken(1).moveToken(board.getTile(5, 5));
            tokenPanel.getWeaponToken(2).moveToken(board.getTile(8, 8));
            tokenPanel.getWeaponToken(3).moveToken(board.getTile(15, 15));
            tokenPanel.getWeaponToken(4).moveToken(board.getTile(10, 15));
            tokenPanel.getWeaponToken(5).moveToken(board.getTile(20, 20));

            tokenPanel.getPlayerToken(0).moveToken(board.getTile(14, 0));
            tokenPanel.getPlayerToken(1).moveToken(board.getTile(23, 6));
            tokenPanel.getPlayerToken(2).moveToken(board.getTile(9, 0));
            tokenPanel.getPlayerToken(3).moveToken(board.getTile(0, 17));
            tokenPanel.getPlayerToken(4).moveToken(board.getTile(23, 19));
            tokenPanel.getPlayerToken(5).moveToken(board.getTile(7, 24));

            tokenPanel.repaint();
        }
    }
    private void initialiseTokens(){
        tokenPanel.addWeaponToken(new Weapon(board.getTile(1, 1), WeaponTypes.PISTOL));
        tokenPanel.addWeaponToken(new Weapon(board.getTile(5, 5), WeaponTypes.ROPE));
        tokenPanel.addWeaponToken(new Weapon(board.getTile(8, 8), WeaponTypes.CANDLESTICK));
        tokenPanel.addWeaponToken(new Weapon(board.getTile(15, 15), WeaponTypes.PIPE));
        tokenPanel.addWeaponToken(new Weapon(board.getTile(10, 15), WeaponTypes.WRENCH));
        tokenPanel.addWeaponToken(new Weapon(board.getTile(20, 20), WeaponTypes.DAGGER));

        tokenPanel.addPlayerToken(new Character(board.getTile(14, 0), CharacterNames.GREEN));
        tokenPanel.addPlayerToken(new Character(board.getTile(23, 6), CharacterNames.PEACOCK));
        tokenPanel.addPlayerToken(new Character(board.getTile(9, 0), CharacterNames.WHITE));
        tokenPanel.addPlayerToken(new Character(board.getTile(0, 17), CharacterNames.MUSTARD));
        tokenPanel.addPlayerToken(new Character(board.getTile(23, 19), CharacterNames.PLUM));
        tokenPanel.addPlayerToken(new Character(board.getTile(7, 24), CharacterNames.SCARLET));

    }
}
