import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JPanel boardPanel;
    JLayeredPane layers;
    TokenController tokenPanel;
    Board board;
    InfoPanel info;
    CmdPanel cmd;

    // Frame constructor
    Frame() {
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
        setLayout(new BorderLayout()); // set the layout to border layer

        add(boardPanel, BorderLayout.CENTER); // add the board to the center of the frame
        add(info, BorderLayout.EAST); // add the info panel to the east of the screen
        add(cmd, BorderLayout.PAGE_END); // add the command panel to the bottom of the screen
    }

    /**
     * Method to test the game screen by placing tokens on the board and moving them.
     */
    public void test(){
        tokenPanel.addWeaponToken(new Weapon(30 ,30, WeaponTypes.PISTOL));
        tokenPanel.addWeaponToken(new Weapon(60 ,40, WeaponTypes.ROPE));
        tokenPanel.addWeaponToken(new Weapon(90 ,50, WeaponTypes.CANDLESTICK));
        tokenPanel.addWeaponToken(new Weapon(120 ,10, WeaponTypes.PIPE));
        tokenPanel.addWeaponToken(new Weapon(110 ,20, WeaponTypes.WRENCH));
        tokenPanel.addWeaponToken(new Weapon(150 ,150, WeaponTypes.DAGGER));

        tokenPanel.addPlayerToken(new Character(30, 60, CharacterNames.GREEN));
        tokenPanel.addPlayerToken(new Character(30, 90, CharacterNames.PLUM));
        tokenPanel.addPlayerToken(new Character(30, 120, CharacterNames.MUSTARD));
        tokenPanel.addPlayerToken(new Character(30, 150, CharacterNames.WHITE));
        tokenPanel.addPlayerToken(new Character(30, 180, CharacterNames.SCARLETT));
        tokenPanel.addPlayerToken(new Character(30, 210, CharacterNames.PEACOCK));

        while (true){
            try{
                Thread.sleep(500);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            tokenPanel.getWeaponToken(0).moveToken(3, 14);
            tokenPanel.getWeaponToken(1).moveToken(3, 14);
            tokenPanel.getWeaponToken(2).moveToken(3, 14);
            tokenPanel.getWeaponToken(3).moveToken(3, 14);
            tokenPanel.getWeaponToken(4).moveToken(13, 14);
            tokenPanel.getWeaponToken(5).moveToken(13, 14);

            //tokenPanel.getPlayerToken(0).moveToken(3, 14);
            //tokenPanel.getPlayerToken(1).moveToken(3, 14);

            tokenPanel.repaint();
        }
    }
}
