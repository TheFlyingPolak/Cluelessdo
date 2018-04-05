import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class IntroScreen extends JPanel {
    private ImageData logo, friends, gunther, whoKilledGunther;
    private Button playButton;
    private PlayerSelectionWindow window;
    private boolean active = true;
    private Timer timer;
    private CircularlyLinkedList<Player> playerList;
    private TokenController tokenPanel;

    public IntroScreen(Board board, TokenController tokenPanel, CircularlyLinkedList<Player> playerList){
        super();
        setOpaque(false);
        setLayout(null);
        setBounds(0, 0, board.getXBoard(), board.getYBoard());
        friends = new ImageData("images/intro/friends.jpg", new Point(0, 0));
        logo = new ImageData("images/intro/logo.png", new Point(0, 201));
        gunther = new ImageData("images/intro/gunther.png", new Point(-111, 367));
        whoKilledGunther = new ImageData("images/intro/who killed gunther.PNG", new Point(301, 321));
        window = new PlayerSelectionWindow();

        playButton = new Button(new Point(getWidth() / 2, 490), "images/intro/play button.png");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClick();
            }
        });
        this.playerList = playerList;
        this.tokenPanel = tokenPanel;
        add(playButton);
        add(window);

        timer = new Timer(20, new AnimationTimerListener());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (active) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(friends.image, friends.position.x, friends.position.y,  this);
            g2.drawImage(logo.image, logo.position.x, logo.position.y, getWidth(), getHeight(), this);
            float opacity = 0.5f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.drawImage(gunther.image, gunther.position.x, gunther.position.y, this);
            opacity = 1f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.drawImage(whoKilledGunther.image, whoKilledGunther.position.x, whoKilledGunther.position.y, this);
        }
    }

    private void playButtonClick(){
        remove(playButton);
        repaint();
        timer.start();
    }

    private void removeScreen(){
        timer = new Timer(20, new ScreenRemovalListener());
        timer.start();
    }

    private class AnimationTimerListener implements ActionListener{
        private int movementIncrement = 1;

        public void actionPerformed(ActionEvent e){
            if (movementIncrement <= 35) {
                gunther.position.x = (int) Easing.easeInQuad(movementIncrement, -111, -getWidth(), 35);
                whoKilledGunther.position.x = (int) Easing.easeInQuad(movementIncrement, 301, getWidth(), 35);
                whoKilledGunther.position.y = (int) Easing.easeInQuad(movementIncrement, 321, Math.tan(-3.18) * getHeight(), 35);
                logo.position.y = (int) Easing.easeInOutQuad(movementIncrement, 201, -70, 35);
            }
            if (movementIncrement >= 25){
                window.setLocation(window.getX(), (int) Easing.easeOutQuad(movementIncrement, 615, -(window.windowImage.getHeight() + 30), 60));
            }
            movementIncrement++;
            repaint();
            if (movementIncrement > 60)
                timer.stop();
        }
    }

    private class ScreenRemovalListener implements ActionListener{
        private int movementIncrement = 1;

        public void actionPerformed(ActionEvent e){
            setLocation(getX(), (int) Easing.easeInQuad(movementIncrement++, 0, -getHeight(), 35));
            if (movementIncrement > 35) {
                timer.stop();
                synchronized (playerList){
                    playerList.notify();
                }
            }
        }
    }

    private class ImageData{
        private BufferedImage image;
        private Point position;

        public ImageData(String path, Point position){
            try {
                image = ImageIO.read(getClass().getResource(path));
                this.position = position;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private class PlayerSelectionWindow extends JComponent{
        private BufferedImage windowImage;
        private final HashMap<CharacterNames, BufferedImage> characterImages = new HashMap<>();
        private int playerCount = 0;
        private JLabel title = new JLabel("Select player " + (playerCount + 1));
        private String playersString = "<html>Players added: </html>";
        private JLabel players = new JLabel(playersString);
        private JTextField playerNameField = new JTextField();
        private CharacterNames cardDisplayingName = CharacterNames.CHANDLER;
        private BufferedImage cardDisplayingImage;
        private Button nextButton, doneButton, leftButton, rightButton;
        private ArrayList<CharacterNames> characterNames = new ArrayList<>();

        public PlayerSelectionWindow(){
            try {
                windowImage = ImageIO.read(getClass().getResource("images/intro/input_window.png"));
                characterImages.put(CharacterNames.CHANDLER, ImageIO.read(getClass().getResource("images/cards/chandler_card.png")));
                characterImages.put(CharacterNames.MONICA, ImageIO.read(getClass().getResource("images/cards/monica_card.png")));
                characterImages.put(CharacterNames.ROSS, ImageIO.read(getClass().getResource("images/cards/ross_card.png")));
                characterImages.put(CharacterNames.PHOEBE, ImageIO.read(getClass().getResource("images/cards/phoebe_card.png")));
                characterImages.put(CharacterNames.JOEY, ImageIO.read(getClass().getResource("images/cards/joey_card.png")));
                characterImages.put(CharacterNames.RACHEL, ImageIO.read(getClass().getResource("images/cards/rachel_card.png")));
            }
            catch (IOException e){
                e.printStackTrace();
            }
            characterNames.addAll(Arrays.asList(CharacterNames.values()));
            cardDisplayingImage = characterImages.get(cardDisplayingName);

            setLayout(null);
            setBounds((592 / 2) - (windowImage.getWidth() / 2), 615, windowImage.getWidth(), windowImage.getHeight());
            title.setBounds((getWidth() / 3) - 70, 20, 140, 30);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setVerticalAlignment(SwingConstants.CENTER);
            add(title);

            players.setBounds((getWidth() / 3) * 2, 20, 140, getHeight() - 40);
            players.setVerticalAlignment(SwingConstants.TOP);
            add(players);

            playerNameField.setBounds((getWidth() / 3) - 100, getHeight() - 40, 200, 20);
            add(playerNameField);

            leftButton = new Button(new Point((getWidth() / 3) - 110, getHeight() / 2), "images/intro/left button.png");
            rightButton = new Button(new Point((getWidth() / 3) + 110, getHeight() / 2), "images/intro/right button.png");
            nextButton = new Button(new Point(getWidth() - 50, getHeight() - 35), "images/intro/next button.png");
            doneButton = new Button(new Point(getWidth() - nextButton.getWidth() - 80, getHeight() - 35), "images/intro/done button.png");

            leftButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    leftButtonAction();
                    repaint();
                }
            });
            rightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightButtonAction();
                    repaint();
                }
            });
            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nextButtonAction();
                    repaint();
                }
            });
            doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doneButtonAction();
                }
            });

            add(leftButton);
            add(rightButton);
            add(nextButton);

            setVisible(true);
        }

        public Dimension getPreferredSize(){
            return new Dimension(windowImage.getWidth(), windowImage.getHeight());
        }
        public Dimension getMinimumSize(){
            return new Dimension(windowImage.getWidth(), windowImage.getHeight());
        }
        public Dimension getMaximumSize(){
            return new Dimension(windowImage.getWidth(), windowImage.getHeight());
        }

        private void leftButtonAction(){
            if (characterNames.indexOf(cardDisplayingName) == 0)
                cardDisplayingName = characterNames.get(characterNames.size() - 1);
            else
                cardDisplayingName = characterNames.get(characterNames.indexOf(cardDisplayingName) - 1);
            cardDisplayingImage = characterImages.get(cardDisplayingName);
        }

        private void rightButtonAction(){
            if (characterNames.indexOf(cardDisplayingName) == characterNames.size() - 1)
                cardDisplayingName = characterNames.get(0);
            else
                cardDisplayingName = characterNames.get(characterNames.indexOf(cardDisplayingName) + 1);
            cardDisplayingImage = characterImages.get(cardDisplayingName);
        }

        private void nextButtonAction(){
            playerList.addLast(new Player(playerNameField.getText(), tokenPanel.getPlayerToken(cardDisplayingName)));
            playerNameField.setText("");
            playersString = playersString.substring(0, playersString.length() - 7);
            playersString += "<br/>" + playerList.getLast().getPlayerToken() + ": " + playerList.getLast().getPlayerName() + "</html>";
            players.setText(playersString);

            rightButtonAction();
            characterNames.remove(playerList.getLast().getPlayerToken().getName());
            title.setText("Select player " + (playerList.getSize() + 1));

            if (playerList.getSize() >= 1)
                add(doneButton);
            if (playerList.getSize() >= 5)
                remove(nextButton);
        }

        private void doneButtonAction(){
            nextButtonAction();
            try{
                cardDisplayingImage = ImageIO.read(getClass().getResource("images/cards/card_back.png"));
            }
            catch (IOException e){
                e.printStackTrace();
            }
            title.setText("Let's play!");
            removeScreen();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.drawImage(windowImage, 0, 0, this);
            g2.drawImage(cardDisplayingImage.getScaledInstance(140, 196, Image.SCALE_DEFAULT), (getWidth() / 3) - (140 / 2), (getHeight() / 2) - (196 / 2), this);

            g2.setFont(new Font("Serif", Font.PLAIN, 24));
        }
    }
}
