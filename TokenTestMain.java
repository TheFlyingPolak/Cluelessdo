import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/*
 * Main class used to test the functionality of all classes responsible for the rendering of tokens on the screen
 *
 * Author: Jakub Gajewski
 */

public class TokenTestMain extends JFrame{
    private TokenController tokenPanel;
    public BufferedImage image;

    public TokenTestMain(){
        /*  Initialise the main window  */
        setTitle("Testing");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*  Create the token controller component on which all tokens will be drawn  */
        tokenPanel = new TokenController();

        /*  Create tokens to test them on the window  */
        Character token1 = new Character(0, 0, CharacterNames.MUSTARD);
        Character token2 = new Character(40, 0, CharacterNames.SCARLET);
        Character token3 = new Character(80 ,0, CharacterNames.PEACOCK);
        Character token4 = new Character(120 ,0, CharacterNames.PLUM);
        Character token5 = new Character(160 ,0, CharacterNames.WHITE);
        Character token6 = new Character(200 ,0, CharacterNames.GREEN);

        Weapon wep1 = new Weapon(240, 0, WeaponTypes.PISTOL);
        Weapon wep2 = new Weapon(280, 50, WeaponTypes.WRENCH);
        Weapon wep3 = new Weapon(320, 0, WeaponTypes.PIPE);
        Weapon wep4 = new Weapon(360, 50, WeaponTypes.ROPE);
        Weapon wep5 = new Weapon(425, 0, WeaponTypes.DAGGER);
        Weapon wep6 = new Weapon(490, 50, WeaponTypes.CANDLESTICK);

        tokenPanel.addPlayerToken(token1);
        tokenPanel.addPlayerToken(token2);
        tokenPanel.addPlayerToken(token3);
        tokenPanel.addPlayerToken(token4);
        tokenPanel.addPlayerToken(token5);
        tokenPanel.addPlayerToken(token6);

        tokenPanel.addWeaponToken(wep1);
        tokenPanel.addWeaponToken(wep2);
        tokenPanel.addWeaponToken(wep3);
        tokenPanel.addWeaponToken(wep4);
        tokenPanel.addWeaponToken(wep5);
        tokenPanel.addWeaponToken(wep6);

        add(tokenPanel);

        setVisible(true);

        /*  Run a test which changes the position of all of the tokens 10 times  */
        while (true){
            try{
                Thread.sleep(500);
            }
            catch (InterruptedException e){

            }

            tokenPanel.getPlayerToken(0).moveToken(0, 2);
            tokenPanel.getPlayerToken(1).moveToken(0, 4);
            tokenPanel.getPlayerToken(2).moveToken(0, 6);
            tokenPanel.getPlayerToken(3).moveToken(0, 8);
            tokenPanel.getPlayerToken(4).moveToken(0, 10);
            tokenPanel.getPlayerToken(5).moveToken(0, 12);

            tokenPanel.getWeaponToken(0).moveToken(0, 14);
            tokenPanel.getWeaponToken(1).moveToken(0, 16);
            tokenPanel.getWeaponToken(2).moveToken(0, 18);
            tokenPanel.getWeaponToken(3).moveToken(0, 20);
            tokenPanel.getWeaponToken(4).moveToken(0, 22);
            tokenPanel.getWeaponToken(5).moveToken(0, 24);

            tokenPanel.repaint();
        }
    }

    public TokenTestMain(String string){
        /*JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        JLayeredPane layers = new JLayeredPane();
        mainPanel.add(layers);

        panel2.setOpaque(true);
        panel1.setOpaque(true);
        panel1.setVisible(true);
        panel2.setVisible(true);
        panel1.setBackground(Color.CYAN);
        panel2.setBackground(Color.BLACK);
        panel1.setBounds(10, 10, 100, 100);
        panel2.setBounds(70, 70, 100, 100);

        setContentPane(mainPanel);
        layers.add(panel1, new Integer(2));
        layers.add(panel2, new Integer(3));

        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/

        try{
            image = ImageIO.read(new File("images/board.jpg"));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        JPanel background = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                if (image != null){
                    g2.drawImage(image, 0, 0, this);
                }
            }
        };
        tokenPanel = new TokenController();

        JLayeredPane layer = new JLayeredPane();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(layer);


        tokenPanel.setOpaque(false);
        background.setBounds(0, 0, 640, 638);
        tokenPanel.setBounds(0, 0, 640, 638);

        add(mainPanel);
        layer.add(background, new Integer(1));
        layer.add(tokenPanel, new Integer(2));

        Weapon token = new Weapon(30 ,30, WeaponTypes.PISTOL);
        tokenPanel.addWeaponToken(token);

        setVisible(true);
        setSize(640, 638);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true){
            try{
                Thread.sleep(500);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            tokenPanel.getWeaponToken(0).moveToken(3, 14);

            tokenPanel.repaint();
        }
    }



    public static void main(String[] args) {
        TokenTestMain window = new TokenTestMain("fdfs");
    }
}
