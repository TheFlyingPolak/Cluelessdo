import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Board board;
    InfoPanel info;
    CmdPanel cmd;

    // Frame constructor
    Frame() {
        super("Cluelessdo");

        board = new Board();
        info = new InfoPanel();
        cmd = new CmdPanel(info);

        setSize(700, 350); // set size of Frame
        setResizable(false); // make frame non resizeable
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //
        setLayout(new BorderLayout());

        add(board, BorderLayout.WEST);
        add(info, BorderLayout.EAST);
        add(cmd, BorderLayout.PAGE_END);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();

        frame.setVisible(true);
    }
}
