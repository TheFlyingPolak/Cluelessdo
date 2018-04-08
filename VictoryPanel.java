import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VictoryPanel extends JComponent {
    private ImageIcon confetti;
    private JLabel confettiLabel;
    private Button exitButton;
    private JLabel nameLabel;
    private JLabel titleLabel;
    private int animationCounter = 0;

    public VictoryPanel(Board board, Player player){
        setBounds(board.getBounds());
        exitButton = new Button(new Point(getWidth() / 2, getHeight() - (getHeight() / 4)), "images/buttons/exit button.png");
        confetti = new ImageIcon(getClass().getResource("images/confetti.gif"));
        confettiLabel = new JLabel(confetti);
        confettiLabel.setBounds(
                (getWidth() / 2) - (confetti.getIconWidth() / 2), (getHeight() / 2) - (confetti.getIconHeight() / 2),
                confetti.getIconWidth(), confetti.getIconHeight());

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        nameLabel = new JLabel("And the winner is");
        nameLabel.setBounds(0, 50, getWidth(), 60);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 50));
        nameLabel.setForeground(Color.RED);
        titleLabel = new JLabel(player.getPlayerName() + "!");
        titleLabel.setBounds(0, 110, getWidth(), 60);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 50));
        titleLabel.setForeground(Color.RED);

        add(exitButton);
        add(confettiLabel);
        add(nameLabel);
        add(titleLabel);
        setVisible(true);

        Audio party = new Audio(Sounds.PARTY);
        Audio win = new Audio((Sounds.INTRO));
    }
}
