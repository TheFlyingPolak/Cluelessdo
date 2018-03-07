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
            chandler, ross, joey, monica, phoebe, rachel;

    /**
     * Constructor of the class TokenController. Initialises the token ArrayLists and attempts to load images.
     * If image is not found, an error message is printed to the console.
     */
    public TokenController(Map map,Board board) throws IOException {
        super(null);
        playerTokens = new ArrayList<>();
        weaponTokens = new ArrayList<>();

        setOpaque(false);

        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        addWeaponToken(new Weapon(map.getTile(11, 11), WeaponTypes.PISTOL));
        addWeaponToken(new Weapon(map.getTile(12, 11), WeaponTypes.ROPE));
        addWeaponToken(new Weapon(map.getTile(13, 11), WeaponTypes.CANDLESTICK));
        addWeaponToken(new Weapon(map.getTile(11, 12), WeaponTypes.PIPE));
        addWeaponToken(new Weapon(map.getTile(12, 12), WeaponTypes.WRENCH));
        addWeaponToken(new Weapon(map.getTile(13, 12), WeaponTypes.DAGGER));

        addPlayerToken(new Character(map.getTile(14, 0), CharacterNames.CHANDLER));
        addPlayerToken(new Character(map.getTile(23, 6), CharacterNames.RACHEL));
        addPlayerToken(new Character(map.getTile(9, 0), CharacterNames.MONICA));
        addPlayerToken(new Character(map.getTile(0, 17), CharacterNames.JOEY));
        addPlayerToken(new Character(map.getTile(23, 19), CharacterNames.ROSS));
        addPlayerToken(new Character(map.getTile(7, 24), CharacterNames.PHOEBE));

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
        chandler = ImageIO.read(getClass().getResource(("images/green.png")));
        ross = ImageIO.read(getClass().getResource(("images/plum.png")));
        joey = ImageIO.read(getClass().getResource(("images/mustard.png")));
        monica = ImageIO.read(getClass().getResource(("images/white.png")));
        phoebe = ImageIO.read(getClass().getResource(("images/scarlett.png")));
        rachel = ImageIO.read(getClass().getResource(("images/peacock.png")));

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
                case PHOEBE:
                    //g2.setColor(Color.RED); break;
                    g2.drawImage(phoebe, dimension.width - (phoebe.getWidth() / 2), dimension.height - (phoebe.getHeight() / 2), this); break;
                case JOEY:
                    //g2.setColor(Color.ORANGE); break;
                    g2.drawImage(joey, dimension.width - (joey.getWidth() / 2), dimension.height - (joey.getHeight() / 2), this); break;
                case RACHEL:
                    //g2.setColor(Color.BLUE); break;
                    g2.drawImage(rachel, dimension.width - (rachel.getWidth() / 2), dimension.height - (rachel.getHeight() / 2), this); break;
                case MONICA:
                    //g2.setColor(Color.WHITE); break;
                    g2.drawImage(monica, dimension.width - (monica.getWidth() / 2), dimension.height - (monica.getHeight() / 2), this); break;
                case CHANDLER:
                    //g2.setColor(Color.GREEN); break;
                    g2.drawImage(chandler, dimension.width - (chandler.getWidth() / 2), dimension.height - (chandler.getHeight() / 2), this); break;
                case ROSS:
                    //g2.setColor(Color.MAGENTA); break;
                    g2.drawImage(ross, dimension.width - (ross.getWidth() / 2), dimension.height - (chandler.getHeight() / 2), this); break;
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
