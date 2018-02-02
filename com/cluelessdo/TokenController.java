package com.cluelessdo;

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

    /**
     * Constructor of the class TokenController. Initialises the token ArrayLists and attemps to load images.
     * If image is not found, an error message is printed to the console.
     */
    public TokenController(){
        super(null);
        playerTokens = new ArrayList<>();
        weaponTokens = new ArrayList<>();

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
            System.out.println("Could not find image for pistol!");
        }

        try{
            rope = ImageIO.read(new File("images/rope.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for pistol!");
        }

        try{
            candlestick = ImageIO.read(new File("images/candlestick.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for pistol!");
        }

        try{
            dagger = ImageIO.read(new File("images/dagger.png"));
        }
        catch (IOException e){
            System.out.println("Could not find image for pistol!");
        }
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
     * Draws all character and weapon tokens using loaded images or predetermined shapes.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        /** Set drawing colour according to character name */
        for (Character tmp: playerTokens){
            switch (tmp.getName()){
                case SCARLET:
                    g2.setColor(Color.RED); break;
                case MUSTARD:
                    g2.setColor(Color.ORANGE); break;
                case PEACOCK:
                    g2.setColor(Color.BLUE); break;
                case WHITE:
                    g2.setColor(Color.WHITE); break;
                case GREEN:
                    g2.setColor(Color.GREEN); break;
                case PLUM:
                    g2.setColor(Color.MAGENTA); break;
            }
            /** Get position of the token on the game board and draw */
            Dimension dimension = tmp.getPosition();
            g2.fillOval(dimension.height, dimension.width, 30, 30);
        }

        /** Draw specified image on the game board according to weapon type */
        for (Weapon tmp: weaponTokens){
            Dimension dimension = tmp.getPosition();
            switch (tmp.getType()){
                case PISTOL:
                    g2.drawImage(pistol, dimension.height, dimension.width, this); break;
                case WRENCH:
                    g2.drawImage(wrench, dimension.height, dimension.width, this); break;
                case PIPE:
                    g2.drawImage(pipe, dimension.height, dimension.width, this); break;
                case DAGGER:
                    g2.drawImage(dagger, dimension.height, dimension.width, this); break;
                case CANDLESTICK:
                    g2.drawImage(candlestick, dimension.height, dimension.width, this); break;
                case ROPE:
                    g2.drawImage(rope, dimension.height, dimension.width, this); break;
            }
        }
    }
}
