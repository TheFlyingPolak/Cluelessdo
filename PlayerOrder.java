import java.util.Arrays;

public class PlayerOrder {
    class DiceRollPlayer {
        private int diceRoll;
        private Player player;

        DiceRollPlayer(Player players) {
            this.player = players;
        }

        public int getDiceRoll() {
            return diceRoll;
        }

        public void setDiceRoll(int diceRoll) {
            this.diceRoll = diceRoll;
        }

        public Player getPlayer() {
            return player;
        }
    }

    private DiceRollPlayer[] diceRoll;
    private UI ui;
    private DicePanel dicePanel;

    PlayerOrder(CircularlyLinkedList<Player> players, UI ui, DicePanel dicePanel) {
        diceRoll = new DiceRollPlayer[players.getSize()];

        int playerNum = 0;

        for (Player player : players) {
            diceRoll[playerNum] = new DiceRollPlayer(player);
            playerNum++;
        }

        this.ui = ui;
        this.dicePanel = dicePanel;
    }

    public CircularlyLinkedList<Player> playerStartOrder(CircularlyLinkedList<Player> players) {
        ui.getInfo().addText("All players will roll the dice to find the order that you will play, whoever rolls the highest number goes first, the second highest next, so on and so forth");
        rollDice(0, diceRoll.length-1);
        sortPlayers(0, diceRoll.length-1);
        checkForTies(0, diceRoll.length-1);

        CircularlyLinkedList<Player> orderedPlayers = new CircularlyLinkedList<Player>();
        for (int i = 0; i < diceRoll.length; i++) {
            System.out.println("name: " + diceRoll[i].getPlayer().getPlayerName() + ", num: " + diceRoll[i].getDiceRoll());
            orderedPlayers.addLast(diceRoll[i].getPlayer());
        }

        return orderedPlayers;
    }

    private void rollDice(int start, int end) {
        int playerNum = 0;

        for (int i = start; i <= end; i++) {
            ui.getInfo().addText(diceRoll[i].getPlayer().getPlayerName() + ", type \"roll\" to roll the dice.");

            String cmdInput = ui.getCmd().getCommand().trim();
            String name = diceRoll[i].getPlayer().getPlayerName();
/*
            while (!cmdInput.equals("roll")) {
                ui.getInfo().addText(name + ", you can only roll the dice right now, try again!");
                cmdInput = ui.getCmd().getCommand();
            }
*/
            int diceRollNum = dicePanel.rollDice();
            ui.getInfo().addText(name + " rolled " + diceRollNum);

            diceRoll[i].setDiceRoll(diceRollNum);
            System.out.println("name: " + name + ", num: " + diceRollNum);
        }
    }

    private void sortPlayers(int start, int end) {
        for (int i = start; i <= end-1; i++) {
            int maxDiceRollIndex = i;
            for (int j = i+1; j <= end; j++) {
                if (diceRoll[j].getDiceRoll() > diceRoll[maxDiceRollIndex].getDiceRoll()) {
                    maxDiceRollIndex = j;
                }
            }

            DiceRollPlayer temp = diceRoll[maxDiceRollIndex];
            diceRoll[maxDiceRollIndex] = diceRoll[i];
            diceRoll[i] = temp;
        }
    }

    private void checkForTies(int start, int end) {
        int player = start;
        while (player < diceRoll.length) {
            int tieIndex = player;

            /* change while loop as its only checking the ones beforehand not the ones after i.e 5, 5, 5 its only checking the first to five and then doing recusion, go other way */

            while (tieIndex < end && diceRoll[tieIndex].getDiceRoll() == diceRoll[tieIndex+1].getDiceRoll()) {
                tieIndex++;
            }

            System.out.println("tieIndex: " + tieIndex + "  PLayer: " + player);

            DiceRollPlayer[] tiePlayers = Arrays.copyOfRange(diceRoll, player, tieIndex);

            if (tieIndex > player) {
                String names = "";
                for (int i = player; i <= tieIndex; i++) {
                    if (i == tieIndex) {
                        System.out.println("Num " + i);
                        names = names.substring(0, names.length()-2) + " and " + diceRoll[i].getPlayer().getPlayerName();
                    } else {
                        System.out.println("Num " + i);
                        names += diceRoll[i].getPlayer().getPlayerName() + ", ";
                    }
                }

                ui.getInfo().addText(names + " have rolled the same number, you will have to roll again");

                rollDice(player, tieIndex);
                sortPlayers(player, tieIndex);
                checkForTies(player, tieIndex);

                player = tieIndex++;
            } else {
                player++;
            }
        }
    }
}
