import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * The TokenController class is an extension of the JPanel class with added functionality to store and
 * render shapes/images using Swing.
 *
 * The class contains 2 array lists, playerTokens and weaponTokens, used to store Character and Weapon objects.
 * Images are stored in an instance of the TokenController class, though this may change in the future.
 *
 * @author Jakub Gajewski
 */

public class TokenController extends JPanel {
    /** ArrayLists used to store Character and Weapon objects */
    public ArrayList<Character> playerTokens;
    public ArrayList<Weapon> weaponTokens;

    /** BufferedImage objects used to store images of weapons in the game */
    private BufferedImage pistol;
    private BufferedImage wrench;
    private BufferedImage pipe;
    private BufferedImage rope;
    private BufferedImage dagger;
    private BufferedImage candlestick;

    private BufferedImage green;
    private BufferedImage plum;
    private BufferedImage mustard;
    private BufferedImage white;
    private BufferedImage scarlett;
    private BufferedImage peacock;


    /**
     * Constructor of the class TokenController. Initialises the token ArrayLists and attempts to load images.
     * If image is not found, an error message is printed to the console.
     */
    public TokenController(){
        super(null);
        playerTokens = new ArrayList<>();
        weaponTokens = new ArrayList<>();

        readImages();
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
     * Methods to find and return a Charaacter or Weapon object by its index in the corresponding list.
     */
    public Character getPlayerToken(int index){
        return playerTokens.get(index);
    }

    public Weapon getWeaponToken(int index){
        return weaponTokens.get(index);
    }

    /**
     * Attempts to read images to be used to represent tokens.
     */
    private void readImages(){
        try{
            green = ImageIO.read(new File("images/green.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for Green!");
        }

        try{
            plum = ImageIO.read(new File("images/plum.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for Plum!");
        }

        try{
            mustard = ImageIO.read(new File("images/mustard.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for Mustard!");
        }

        try{
            white = ImageIO.read(new File("images/white.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for White!");
        }

        try{
            scarlett = ImageIO.read(new File("images/scarlett.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for Scarlett!");
        }

        try{
            peacock = ImageIO.read(new File("images/peacock.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for Peacock!");
        }

        try{
            pistol = ImageIO.read(new File("images/pistol.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for pistol!");
        }

        try{
            wrench = ImageIO.read(new File("images/wrench.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for wrench!");
        }

        try{
            pipe = ImageIO.read(new File("images/pipe.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for pipe!");
        }

        try{
            rope = ImageIO.read(new File("images/rope.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for rope!");
        }

        try{
            candlestick = ImageIO.read(new File("images/candlestick.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for candlestick!");
        }

        try{
            dagger = ImageIO.read(new File("images/dagger.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for dagger!");
        }
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
            /** Get position of the token on the game board and draw */
            //Dimension dimension = tmp.getPosition();
            //g2.fillOval(dimension.height, dimension.width, 21, 21);
        }

        /** Draw specified image on the game board according to weapon type */
        for (Weapon tmp: weaponTokens){
            Dimension dimension = tmp.getPosition();
            switch (tmp.getType()){
                case PISTOL:
                    g2.drawImage(pistol, dimension.width - (pistol.getWidth() / 2), dimension.height - (pistol.getHeight() / 2), this); break;
                case WRENCH:
                    g2.drawImage(wrench, dimension.width - (wrench.getWidth() / 2), dimension.height - (wrench.getHeight() / 2), this); break;
                case PIPE:
                    g2.drawImage(pipe, dimension.width - (pipe.getWidth() / 2), dimension.height - (pipe.getHeight() / 2), this); break;
                case DAGGER:
                    g2.drawImage(dagger, dimension.width - (dagger.getWidth() / 2), dimension.height - (dagger.getHeight() / 2), this); break;
                case CANDLESTICK:
                    g2.drawImage(candlestick, dimension.width - (candlestick.getWidth() / 2), dimension.height - (candlestick.getHeight() / 2), this); break;
                case ROPE:
                    g2.drawImage(rope, dimension.width - (rope.getWidth() / 2), dimension.height - (rope.getHeight() / 2), this); break;
            }
        }
    }
}
