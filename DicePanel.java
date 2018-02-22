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
    private BufferedImage[] dice = new BufferedImage[6];
    private Thread t;

    private int xPosition1, yPosition1, xPosition2, yPosition2;
    private int diceNumber1, diceNumber2;
    private Random random = new Random();
    private boolean rolling = false;

    private final String imagePath = "images/dice/dice";

    public DicePanel(Board board) throws IOException{
        super();
        setOpaque(false);
        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        for (int i = 0; i < 6; i++){
            dice[i] = ImageIO.read(getClass().getResource(imagePath + (i + 1) + ".png"));
        }
    }

    public int rollDice(){
        int lastRolledNumber1 = 0;
        int lastRolledNumber2 = 0;
        rolling = true;

        for (int i = 0; i < 12; i++){
            /** Generate random dice numbers */
            diceNumber1 = random.nextInt(6) + 1;
            if (diceNumber1 == lastRolledNumber1){
                switch (diceNumber1){
                    case 1: diceNumber1 = random.nextInt(5) + 2; break;
                    case 6: diceNumber1 = random.nextInt(5) + 1; break;
                    default: diceNumber1 = random.nextBoolean() ? random.nextInt(diceNumber1 - 1) + 1 : random.nextInt(6 - diceNumber1) + 1;
                }
            }

            diceNumber2 = random.nextInt(6) + 1;
            if (diceNumber2 == lastRolledNumber2){
                switch (diceNumber2){
                    case 1: diceNumber2 = random.nextInt(5) + 2; break;
                    case 6: diceNumber2 = random.nextInt(5) + 1; break;
                    default: diceNumber2 = random.nextBoolean() ? random.nextInt(diceNumber2 - 1) + 1 : random.nextInt(6 - diceNumber2) + 1;
                }
            }

            xPosition1 = 100 + (18 * i);
            yPosition1 = 40 + (18 * i);

            xPosition2 = 40 + (18 * i);
            yPosition2 = 100 + (18 * i);

            repaint();
            lastRolledNumber1 = diceNumber1;
            lastRolledNumber2 = diceNumber2;
            try{
                Thread.sleep(100 + (i * 10));
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        rolling = false;
        repaint();
        return diceNumber1 + diceNumber2;
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
            BufferedImage dice1 = dice[diceNumber1 - 1];
            BufferedImage dice2 = dice[diceNumber2 - 1];

            double rotationAngle = Math.PI * 2 * random.nextDouble();
            AffineTransform transform = AffineTransform.getRotateInstance(rotationAngle, xPosition1, yPosition1);
            g2.setTransform(transform);
            g2.drawImage(dice1, xPosition1 - (dice1.getWidth() / 2), yPosition1 - (dice1.getHeight() / 2), this);

            rotationAngle = Math.PI * 2 * random.nextDouble();
            transform = AffineTransform.getRotateInstance(rotationAngle, xPosition2, yPosition2);
            g2.setTransform(transform);
            g2.drawImage(dice2, xPosition2 - (dice2.getWidth() / 2), yPosition2 - (dice2.getHeight() / 2), this);
        }
    }
}