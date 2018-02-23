
/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

import java.io.IOException;
import java.util.Arrays;

public class Cluelessdo {
    private TokenController tokenPanel;
    private DicePanel dicePanel;
    private UI ui;
    private CircularlyLinkedList players;
    private int numberOfPlayers = 0;

    Cluelessdo() throws IOException {
        ui = new UI();
        tokenPanel = new TokenController(ui.getBoard());     // Token drawing panel
        dicePanel = new DicePanel(ui.getBoard());
        ui.getLayers().add(tokenPanel, Integer.valueOf(2));
        ui.getLayers().add(dicePanel, Integer.valueOf(3));
        players = new CircularlyLinkedList();
    }

    /**
     * checks to see if the move is valid, if valid moves the player, if not prints error message onto info panel
     * @return returns false if move is illegal, true if move has been made successfully
     */
    public boolean movePlayer(int playerIndex, Direction dir) {
        if (!tokenPanel.getPlayerTokens().get(playerIndex).moveToken(dir, ui.getBoard())) { // move the player and check if not successful
            String playerName = tokenPanel.getPlayerTokens().get(playerIndex).getName().toString(); // get the players name
            playerName = playerName.substring(0, 1) + playerName.substring(1).toLowerCase(); // capitalise the first letter and set the rest to lower case
            String errorMessage = playerName + " cannot move " + dir.toString().toLowerCase(); // make error message
            ui.getInfo().addText(errorMessage); // add error message to info panel
            return false; // move not successful
        }
        return true; // move successful
    }

    /**
     * Checks if the parameter is a valid game command string. If yes, executes the command.
     */
    private void doCommand(String command){
        switch (command){
            case "roll":
                ui.getInfo().addText("Rolling...");
                ui.getInfo().addText("You rolled " + dicePanel.rollDice());
                break;
            case "u":
                break;
            case "d":
                break;
            case "l":
                break;
            case "r":
                break;
            case "done":
                break;
            case "passage":
                break;
            case "quit":
                System.exit(0);
                break;
            default:
                ui.getInfo().addText("Invalid command: \"" + command + "\"");
                break;
        }
    }

    /**
     * Method to be executed at the start of the game to initialise the list of human players who will play.
     */
    private void enterPlayers(){
        String commandLineInput;
        ui.getInfo().addText("Welcome to Cluelessdo! How many players will be playing? (2-6)");

        /* Ask for the number of players until the user enters a valid number */
        do{
            commandLineInput = ui.getCmd().getCommand();
            try {
                numberOfPlayers = Integer.parseInt(commandLineInput);
            }
            catch (NumberFormatException e){
                ui.getInfo().addText("Your input, \"" + commandLineInput + "\" is not a number");
                continue;
            }
            if (numberOfPlayers < 0)
                ui.getInfo().addText("It's not possible to play with less than 1 players! Try a different number.");
            else if (numberOfPlayers == 1)
                ui.getInfo().addText("Playing by yourself is quite pointless isn't it? Bring some friends and try again!");
            else if (numberOfPlayers > 6)
                ui.getInfo().addText("Whoa! We can't fit " + numberOfPlayers + " players on the board! Try 6 or less!");
            else
                ui.getInfo().addText(numberOfPlayers + " players playing! Let's get to know them!");
        } while (numberOfPlayers < 2 || numberOfPlayers > 6);

        // Temporary array used to check whether a player has already chosen a character
        CharacterNames[] characterNames = new CharacterNames[numberOfPlayers];

        /* Ask all players to input their name and select the character they wish to play as */
        for (int i = 0; i < numberOfPlayers; i++){
            /* Enter player name */
            String name;
            String character;
            ui.getInfo().addText("Player " + (i + 1) + ", what's your name?");
            commandLineInput = ui.getCmd().getCommand();
            name = commandLineInput;

            /* Select playable character */
            ui.getInfo().addText("Who would you like to play as, " + name + "?");
            boolean loop = true;
            do{
                character = ui.getCmd().getCommand().toLowerCase();
                switch (character){
                    case "mustard":
                    case "joey":
                        if (Arrays.asList(characterNames).contains(CharacterNames.MUSTARD)) {
                            ui.getInfo().addText("Someone has already chosen Mustard. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.MUSTARD;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.MUSTARD)));
                        loop = false;
                        break;
                    case "scarlet":
                    case "phoebe":
                        if (Arrays.asList(characterNames).contains(CharacterNames.SCARLET)){
                            ui.getInfo().addText("Someone has already chosen Scarlet. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.SCARLET;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.SCARLET)));
                        loop = false;
                        break;
                    case "white":
                    case "monica":
                        if (Arrays.asList(characterNames).contains(CharacterNames.WHITE)){
                            ui.getInfo().addText("Someone has already chosen White. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.WHITE;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.WHITE)));
                        loop = false;
                        break;
                    case "green":
                    case "chandler":
                        if (Arrays.asList(characterNames).contains(CharacterNames.GREEN)){
                            ui.getInfo().addText("Someone has already chosen Green. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.GREEN;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.GREEN)));
                        loop = false;
                        break;
                    case "plum":
                    case "ross":
                        if (Arrays.asList(characterNames).contains(CharacterNames.PLUM)){
                            ui.getInfo().addText("Someone has already chosen Plum. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.PLUM;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.PLUM)));
                        loop = false;
                        break;
                    case "peacock":
                    case "rachel":
                        if (Arrays.asList(characterNames).contains(CharacterNames.PEACOCK)){
                            ui.getInfo().addText("Someone has already chosen Peacock. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.PEACOCK;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.PEACOCK)));
                        loop = false;
                        break;
                    default:
                        ui.getInfo().addText(character + " is not a valid character in this game");
                }
            } while (loop);
        }
        ui.getInfo().addText("All players ready! Type \"play\" to begin!");

        do{
        } while (!ui.getCmd().getCommand().equals("play"));
    }

    public static void main(String[] args) throws IOException {
        Cluelessdo game = new Cluelessdo();
        game.tokenPanel.repaint();
        game.ui.setVisible(true);

        game.enterPlayers();

        while (true){
            String command = game.ui.getCmd().getCommand();
            game.doCommand(command);
        }
    }
}