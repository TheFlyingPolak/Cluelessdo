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
    private int[] diceNum;
    private Random random = new Random();
    private boolean rolling = false;

    private final String imagePath = "images/dice/dice";

    public DicePanel(Board board) throws IOException{
        super();
        diceNum = new int[2];
        setOpaque(false);
        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        for (int i = 0; i < 6; i++){
            dice[i] = ImageIO.read(getClass().getResource(imagePath + (i + 1) + ".png"));
        }
    }

    public int rollDice(){
        int[] lastRolledNumber = new int[2];
        rolling = true;

        for (int i = 0; i < 12; i++){
            /** Generate random dice numbers */
            for (int j = 0; j < diceNum.length; j++) {
                diceNum[j] = random.nextInt(6) + 1;
                if (diceNum[j] == lastRolledNumber[j]) {
                    switch (diceNum[j]) {
                        case 1:
                            diceNum[j] = random.nextInt(5) + 2;
                            break;
                        case 6:
                            diceNum[j] = random.nextInt(5) + 1;
                            break;
                        default:
                            diceNum[j] = random.nextBoolean() ? random.nextInt(diceNum[j] - 1) + 1 : random.nextInt(6 - diceNum[j]) + 1;
                    }
                }
                lastRolledNumber[i] = diceNum[i];
            }

            xPosition1 = 100 + (18 * i);
            yPosition1 = 40 + (18 * i);

            xPosition2 = 40 + (18 * i);
            yPosition2 = 100 + (18 * i);

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
        return diceNum[0] + diceNum[1];
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
            BufferedImage[] dices = {dice[diceNum[0] - 1], dice[diceNum[1] - 1]};

            for (int i = 0; i < dices.length; i++) {
                double rotationAngle = Math.PI * 2 * random.nextDouble();
                AffineTransform transform = AffineTransform.getRotateInstance(rotationAngle, xPosition1, yPosition1);
                g2.setTransform(transform);
                g2.drawImage(dices[i], xPosition1 - (dices[i].getWidth() / 2), yPosition1 - (dices[i].getHeight() / 2), this);
            }
        }
    }
}