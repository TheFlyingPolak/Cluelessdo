import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class Log {
    private class LogElement{
        private Player playerAsking;
        private String cardShown;
        private Player playerShowingCard;

        public LogElement(Player playerAsking, String card, Player playerShowing){
            this.playerAsking = playerAsking;
            cardShown = card;
            playerShowingCard = playerShowing;
        }

        public LogElement(Player playerAsking){
            this.playerAsking = playerAsking;
        }
    }
    private static final int TEXT_AREA_HEIGHT = 38;
    private static final int CHARACTER_WIDTH = 60;
    private static final int FONT_SIZE = 12;
    private final LinkedList<LogElement> list = new LinkedList<>();

    public void addEntryWithReply(Player playerAsking, String card, Player playerShowing){
        list.add(new LogElement(playerAsking, card, playerShowing));
    }

    public void addEntryWithoutReply(Player playerAsking){
        list.add(new LogElement(playerAsking));
    }

    public void showLog(Player playerRequesting){
        JFrame log = new JFrame();
        JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        log.setSize(400, 500);
        log.setLocation(1000,0);
        log.setResizable(false);

        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(getLog(playerRequesting));

        log.setLayout(new BorderLayout());
        log.add(scrollPane, BorderLayout.CENTER);
        log.setVisible(true);
    }

    private String getLog(Player playerRequesting){
        String log = "";
        if (list.isEmpty())
            log += "Log is empty.";
        else {
            for (LogElement element : list) {
                if (element.cardShown == null)
                    log += "• " + element.playerAsking.getPlayerName() + " is shown no card\n";
                else {
                    if (element.playerAsking == playerRequesting || element.playerShowingCard == playerRequesting)
                        log += "• " + element.playerAsking.getPlayerName() + " is shown " + element.cardShown + " by " + element.playerShowingCard.getPlayerName() + "\n";
                    else
                        log += "• " + element.playerAsking.getPlayerName() + " is shown *** by " + element.playerShowingCard.getPlayerName() + "\n";
                }
            }
        }
        return log;
    }
}
