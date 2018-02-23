import javax.swing.*;
import java.awt.*;

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class BoardPanel extends JPanel {
    private Image image;

    public BoardPanel(Image image){
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
