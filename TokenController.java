import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

/*
 * The TokenController class is an extension of the JPanel class with added functionality to store and
 * render shapes/images using Swing.
 *
 * The class contains 2 array lists, playerTokens and weaponTokens, used to store Character and Weapon objects.
 * Images are stored in an instance of the TokenController class, though this may change in the future.
 */

public class TokenController extends JPanel {
    /** ArrayLists used to store Character and Weapon objects */
    private ArrayList<Character> playerTokens;
    private ArrayList<Weapon> weaponTokens;

    /** BufferedImage objects used to store images of weapons in the game */
    private BufferedImage pistol, wrench, pipe, rope, dagger, candlestick,
            green, plum, mustard, white, scarlett, peacock;

    /**
     * Constructor of the class TokenController. Initialises the token ArrayLists and attempts to load images.
     * If image is not found, an error message is printed to the console.
     */
    public TokenController(Board board) throws IOException {
        super(null);
        playerTokens = new ArrayList<>();
        weaponTokens = new ArrayList<>();

        setOpaque(false);

        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        addWeaponToken(new Weapon(board.getTile(1, 1), WeaponTypes.PISTOL));
        addWeaponToken(new Weapon(board.getTile(5, 5), WeaponTypes.ROPE));
        addWeaponToken(new Weapon(board.getTile(8, 8), WeaponTypes.CANDLESTICK));
        addWeaponToken(new Weapon(board.getTile(15, 15), WeaponTypes.PIPE));
        addWeaponToken(new Weapon(board.getTile(10, 15), WeaponTypes.WRENCH));
        addWeaponToken(new Weapon(board.getTile(20, 20), WeaponTypes.DAGGER));

        addPlayerToken(new Character(board.getTile(14, 0), CharacterNames.GREEN));
        addPlayerToken(new Character(board.getTile(23, 6), CharacterNames.PEACOCK));
        addPlayerToken(new Character(board.getTile(9, 0), CharacterNames.WHITE));
        addPlayerToken(new Character(board.getTile(0, 17), CharacterNames.MUSTARD));
        addPlayerToken(new Character(board.getTile(23, 19), CharacterNames.PLUM));
        addPlayerToken(new Character(board.getTile(7, 24), CharacterNames.SCARLET));

        readImages();
    }

    public ArrayList<Character> getPlayerTokens() {
        return playerTokens;
    }

    public ArrayList<Weapon> getWeaponTokens() {
        return weaponTokens;
    }

    /**
     * Methods to add a Character or Weapon object to the corresponding list.
     */
    public void addPlayerToken(Character token){
        playerTokens.add(token);
    }

    public void addWeaponToken(Weapon token){
        weaponTokens.add(token);
    }

    /**
     * Methods to find and return a Character or Weapon object by its type enum.
     */
    public Character getPlayerToken(CharacterNames name) {
        for (Character tmp: playerTokens){
            if (tmp.getName() == name)
                return tmp;
        }
        return null;
    }

    public Weapon getWeaponToken(WeaponTypes type){
        for (Weapon tmp: weaponTokens){
            if (tmp.getType() == type)
                return tmp;
        }
        return null;
    }

    /**
     * Methods to find and return a Character or Weapon by its type.
     */

    /**
     * Attempts to read images to be used to represent tokens.
     */
    private void readImages() throws IOException {
        green = ImageIO.read(getClass().getResource(("images/green.png")));
        plum = ImageIO.read(getClass().getResource(("images/plum.png")));
        mustard = ImageIO.read(getClass().getResource(("images/mustard.png")));
        white = ImageIO.read(getClass().getResource(("images/white.png")));
        scarlett = ImageIO.read(getClass().getResource(("images/scarlett.png")));
        peacock = ImageIO.read(getClass().getResource(("images/peacock.png")));

        pistol = ImageIO.read(getClass().getResource(("images/pistol.png")));
        wrench = ImageIO.read(getClass().getResource(("images/wrench.png")));
        pipe = ImageIO.read(getClass().getResource(("images/pipe.png")));
        candlestick = ImageIO.read(getClass().getResource(("images/candlestick.png")));
        rope = ImageIO.read(getClass().getResource(("images/rope.png")));
        dagger = ImageIO.read(getClass().getResource(("images/dagger.png")));
    }

    /**
     * Draws all character and weapon tokens using loaded images or predetermined shapes.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        /** Set drawing colour according to character name */
        for (Character tmp: playerTokens){
            Dimension dimension = tmp.getPosition();
            switch (tmp.getName()){
                case SCARLET:
                    //g2.setColor(Color.RED); break;
                    g2.drawImage(scarlett, dimension.width - (scarlett.getWidth() / 2), dimension.height - (scarlett.getHeight() / 2), this); break;
                case MUSTARD:
                    //g2.setColor(Color.ORANGE); break;
                    g2.drawImage(mustard, dimension.width - (mustard.getWidth() / 2), dimension.height - (mustard.getHeight() / 2), this); break;
                case PEACOCK:
                    //g2.setColor(Color.BLUE); break;
                    g2.drawImage(peacock, dimension.width - (peacock.getWidth() / 2), dimension.height - (peacock.getHeight() / 2), this); break;
                case WHITE:
                    //g2.setColor(Color.WHITE); break;
                    g2.drawImage(white, dimension.width - (white.getWidth() / 2), dimension.height - (white.getHeight() / 2), this); break;
                case GREEN:
                    //g2.setColor(Color.GREEN); break;
                    g2.drawImage(green, dimension.width - (green.getWidth() / 2), dimension.height - (green.getHeight() / 2), this); break;
                case PLUM:
                    //g2.setColor(Color.MAGENTA); break;
                    g2.drawImage(plum, dimension.width - (plum.getWidth() / 2), dimension.height - (green.getHeight() / 2), this); break;
            }
        }

        /** Draw specified image on the game board according to weapon type */
        for (Weapon tmp: weaponTokens){
            Dimension dimension = tmp.getPosition();
            switch (tmp.getType()){
                case PISTOL:
                    g2.drawImage(pistol, dimension.width - (pistol.getWidth() / 2), dimension.height - (pistol.getHeight() / 2), this);
                    break;
                case WRENCH:
                    g2.drawImage(wrench, dimension.width - (wrench.getWidth() / 2), dimension.height - (wrench.getHeight() / 2), this);
                    break;
                case PIPE:
                    g2.drawImage(pipe, dimension.width - (pipe.getWidth() / 2), dimension.height - (pipe.getHeight() / 2), this);
                    break;
                case DAGGER:
                    g2.drawImage(dagger, dimension.width - (dagger.getWidth() / 2), dimension.height - (dagger.getHeight() / 2), this);
                    break;
                case CANDLESTICK:
                    g2.drawImage(candlestick, dimension.width - (candlestick.getWidth() / 2), dimension.height - (candlestick.getHeight() / 2), this);
                    break;
                case ROPE:
                    g2.drawImage(rope, dimension.width - (rope.getWidth() / 2), dimension.height - (rope.getHeight() / 2), this);
                    break;
            }
        }
    }
}
