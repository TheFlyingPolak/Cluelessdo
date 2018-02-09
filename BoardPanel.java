import javax.swing.*;
import java.awt.*;

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
