import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * The DicePanel class simulates the throwing of dice by drawing images of dice on a JPanel and cycling through
 * random numbers.
 */

public class DicePanel extends JPanel implements Runnable{
    /*
     * Class Die stores position and toss result for an individual die.
     */
    private class Die{
        public int xPosition, yPosition, number, lastRolledNumber;

        public Die(){
        }
    }

    private BufferedImage[] diceImages = new BufferedImage[6];
    private Thread t;
    private final int NUMBER_OF_DICE = 2;
    private Die[] dice;
    private Random random = new Random();
    private boolean rolling = false;        // Used by paintComponent method to draw dice if true
    private final String imagePath = "images/dice/dice";

    public DicePanel(Board board) throws IOException{
        super();
        dice = new Die[NUMBER_OF_DICE];
        for (int i = 0; i < NUMBER_OF_DICE; i++)
            dice[i] = new Die();

        setOpaque(false);
        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        for (int i = 0; i < 6; i++){
            diceImages[i] = ImageIO.read(getClass().getResource(imagePath + (i + 1) + ".png"));
        }
    }

    public int rollDice(){
        rolling = true;

        for (int i = 0; i < 12; i++){
            /** Generate random dice numbers */
            for (int j = 0; j < NUMBER_OF_DICE; j++) {
                dice[j].number = random.nextInt(6) + 1;
                if (dice[j].number == dice[j].lastRolledNumber) {
                    switch (dice[j].number) {
                        case 1:
                            dice[j].number = random.nextInt(5) + 2;
                            break;
                        case 6:
                            dice[j].number = random.nextInt(5) + 1;
                            break;
                        default:
                            dice[j].number = random.nextBoolean() ? random.nextInt(dice[j].number - 1) + 1 : random.nextInt(6 - dice[j].number) + 1;
                    }
                }
                dice[j].lastRolledNumber = dice[j].number;
            }

            /** Set starting positions for dice on the board */
            dice[0].xPosition = 100 + (18 * i);
            dice[0].yPosition = 40 + (18 * i);

            dice[1].xPosition = 40 + (18 * i);
            dice[1].yPosition = 100 + (18 * i);

            repaint();
            try{
                Thread.sleep(100 + (i * 10));
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        rolling = false;
        repaint();
        return getTotalDiceNumber();
    }

    private int getTotalDiceNumber(){
        int totalNumber = 0;
        for (int i = 0; i < NUMBER_OF_DICE; i++)
            totalNumber += dice[i].number;
        return totalNumber;
    }

    public void run(){
        rollDice();
    }

    public void start(){
        t = new Thread(this, "Dice roll thread");
        t.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (rolling) {
            BufferedImage[] diceToDraw = new BufferedImage[NUMBER_OF_DICE];
            for (int i = 0; i < NUMBER_OF_DICE; i++) {
                diceToDraw[i] = diceImages[dice[i].number - 1];
                double rotationAngle = Math.PI * 2 * random.nextDouble();
                AffineTransform transform = AffineTransform.getRotateInstance(rotationAngle, dice[i].xPosition, dice[i].yPosition);
                g2.setTransform(transform);
                g2.drawImage(diceToDraw[i], dice[i].xPosition - (diceToDraw[i].getWidth() / 2), dice[i].yPosition - (diceToDraw[i].getHeight() / 2), this);
            }
        }
    }
}