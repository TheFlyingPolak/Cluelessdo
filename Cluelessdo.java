
/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Cluelessdo {
    private TokenController tokenPanel;
    private DicePanel dicePanel;
    private UI ui;
    private CircularlyLinkedList<Player> players;
    private Iterator<Player> playerIterator;
    private int numberOfPlayers = 0;
    private boolean running;

    Cluelessdo() throws IOException {
        ui = new UI();
        tokenPanel = new TokenController(ui.getBoard());     // Token drawing panel
        dicePanel = new DicePanel(ui.getBoard());
        ui.getLayers().add(tokenPanel, Integer.valueOf(2));
        ui.getLayers().add(dicePanel, Integer.valueOf(3));
        players = new CircularlyLinkedList<>();
        running = true;
    }

    /**
     * checks to see if the move is valid, if valid moves the player, if not prints error message onto info panel
     * @return returns false if move is illegal, true if move has been made successfully
     */
    public boolean moveCharacter(Character playerToken, Direction dir) {
        if (!playerToken.moveToken(dir, ui.getBoard())) { // move the player and check if not successful
            String playerName = playerToken.getName().toString(); // get the players name
            playerName = playerName.substring(0, 1) + playerName.substring(1).toLowerCase(); // capitalise the first letter and set the rest to lower case
            String errorMessage = playerName + " cannot move " + dir.toString().toLowerCase(); // make error message
            ui.getInfo().addText(errorMessage); // add error message to info panel
            return false; // move not successful
        }
        tokenPanel.repaint();
        return true; // move successful
    }

    /**
     * checks to see if the room occupied by the player has a secret passage and attempts to use it if it does
     * @return false if move is illegal
     */
    public boolean moveSecretPassage(Character playerToken) {
        Room currRoom = ui.getBoard().getRoom(playerToken.getCurrentTile().getRoomType().ordinal()); // get the room that the player is currently in
        if (currRoom.hasSecretPasssage()) {
            Room nextRoom = currRoom.getSecretPassage(); // get the room that the secret passage brings players to
            playerToken.moveToken(nextRoom.addToken()); // move the player to the token in the room that the secret passage is connected to
            tokenPanel.repaint();
            return true; // successful
        } else {
            return false; // cannot move player
        }
    }

    public TokenController getTokenPanel() {
        return tokenPanel;
    }

    public UI getUi() {
        return ui;
    }

    public boolean isRunning(){
        return running;
    }

    /**
     * Gets a string from the command panel and checks if it is a valid game command string. If yes, returns
     * a CommandTypes value corresponding to that command string.
     * For the command "quit" prompts the user to confirm and ends the program.
     */
    private CommandTypes doCommand(){
        String command = ui.getCmd().getCommand().toLowerCase();
        switch (command){
            case "roll": return CommandTypes.ROLL;
            case "u": case "up": return CommandTypes.MOVE_UP;
            case "d": case "down": return CommandTypes.MOVE_DOWN;
            case "l": case "left": return CommandTypes.MOVE_LEFT;
            case "r": case "right": return CommandTypes.MOVE_RIGHT;
            case "done": return CommandTypes.DONE;
            case "passage": case "pass": return CommandTypes.PASSAGE;
            case "quit": case "exit":
                ui.getInfo().addText("Are you sure you want to quit? (y/n)");
                boolean loop = true;
                do{
                    switch (ui.getCmd().getCommand()){
                        case "y":
                        case "yes":
                            System.exit(0);
                            break;
                        case "n":
                        case "no":
                            ui.getInfo().addText("Welcome back to the game!");
                            loop = false;
                            break;
                    }
                } while (loop);
                break;
            default:
                ui.getInfo().addText("Invalid command: \"" + command + "\"");
                return CommandTypes.INVALID;
        }
        return CommandTypes.INVALID;
    }

    /**
     * Method to be executed at the start of the game to initialise the list of human players who will play.
     */
    private void enterPlayers(){
        String commandLineInput;

        /** Ask for the number of players until the user enters a valid number */
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

        /** Ask all players to input their name and select the character they wish to play as */
        for (int i = 0; i < numberOfPlayers; i++){
            /* Enter player name */
            String name;
            String character;
            ui.getInfo().addText("Player " + (i + 1) + ", what's your name?");
            commandLineInput = ui.getCmd().getCommand();
            name = commandLineInput;

            /** Select playable character */
            ui.getInfo().addText("Who would you like to play as, " + name + "? Available characters:");
            final CharacterNames[] allCharacterNames = {CharacterNames.MUSTARD, CharacterNames.WHITE, CharacterNames.GREEN, CharacterNames.SCARLET, CharacterNames.PEACOCK, CharacterNames.PLUM};
            for (int j = 0; j < 6; j++){
                if (!Arrays.asList(characterNames).contains(allCharacterNames[j]))
                    ui.getInfo().addText(allCharacterNames[j].toString());
            }
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

        ui.getInfo().clear();
        playerIterator = players.iterator();
    }

    /**
     * Method to control the progression of a single turn, i.e. dice roll, all movement and number of moves remaining
     * @param currentPlayer the player to take the turn
     */
    public void playTurn(Player currentPlayer){
        CommandTypes command;
        int numberOfMoves = 0;
        ui.getInfo().addText(currentPlayer.getPlayerName() + ", it's your turn! Type \"roll\" to roll the dice.");

        /** Execute dice roll and record the number on the dice */
        do{
            command = doCommand();
            if (command == CommandTypes.ROLL){
                numberOfMoves = dicePanel.rollDice();
                ui.getInfo().addText("You rolled " + numberOfMoves);
            }
        } while (command != CommandTypes.ROLL);

        /** Overall control over player action after the dice roll. Main loop repeats until numberOfMoves
         *  reaches 0 through exhaustion of moves or the user typing "done" */
        do{
            /** Ask player to end the turn once numberOfMoves == 0 and the loop has repeated */
            if (numberOfMoves == 0){
                do{
                    ui.getInfo().addText("You are out of moves! Type \"done\" to end your turn.");
                    command = doCommand();
                } while (command != CommandTypes.DONE);
            }
            /** If player is in a room, ask player to choose an exit or use a secret passage */
            else if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR){
                ui.getInfo().addText("Select exit to take or use secret passage");

                /** Collect information about the room the player is occupying */
                int numberOfRoomExits = ui.getBoard().getRoomByType(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()).getNumberOfDoors();
                Tile[] doors = new Tile[numberOfRoomExits];
                for (int i = 0; i < numberOfRoomExits; i++) {
                    doors[i] = ui.getBoard().getRoomByType(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()).getDoor(i);
                    ui.getInfo().addText("Door " + (i + 1) + ": " + doors[i].getTileX() + "," + doors[i].getTileY());
                }

                /** Prompt user to select exit or secret passage */
                boolean loop = true;
                do{
                    String commandString = ui.getCmd().getCommand();
                    /** User selects secret passage */
                    if (commandString.equals("pass") || commandString.equals("passage")){
                        if (moveSecretPassage(currentPlayer.getPlayerToken())) {
                            numberOfMoves = 0;
                            loop = false;
                        }
                        else
                            ui.getInfo().addText("This room does not have a secret passage!");
                    }
                    /** User does not select passage. Check whether user input matches a door index */
                    else {
                        try {
                            int doorSelection = Integer.parseInt(commandString);
                            if (doorSelection < 1 || doorSelection > numberOfRoomExits)
                                ui.getInfo().addText("Please choose a number between 1 and " + numberOfRoomExits);
                            else{
                                if (!currentPlayer.getPlayerToken().moveOutOfRoom(doors[doorSelection - 1]))
                                    ui.getInfo().addText("This door seems to be blocked. Choose a different one.");
                                else {
                                    tokenPanel.repaint();
                                    numberOfMoves--;
                                    loop = false;
                                }
                            }
                        }
                        catch (NumberFormatException e){
                            ui.getInfo().addText("Invalid input: \"" + commandString + "\"");
                        }
                    }
                } while (loop);
            }
            /** If player is not in a room, continue normal progression of movement */
            else{
                command = doCommand();
                switch (command) {
                    case MOVE_UP:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.UP))
                            numberOfMoves--;
                        break;
                    case MOVE_DOWN:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.DOWN))
                            numberOfMoves--;
                        break;
                    case MOVE_LEFT:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.LEFT))
                            numberOfMoves--;
                        break;
                    case MOVE_RIGHT:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.RIGHT))
                            numberOfMoves--;
                        break;
                    case PASSAGE:
                        ui.getInfo().addText("Cannot use secret passage: you are not in a room");
                    case DONE:
                        ui.getInfo().addText("You have ended your turn!");
                        numberOfMoves = 0;
                        break;
                }
                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR)
                    numberOfMoves = 0;
            }
        } while (command != CommandTypes.DONE);
    }

    public static void main(String[] args) throws IOException {
        Cluelessdo game = new Cluelessdo();
        Player currentPlayer;
        game.tokenPanel.repaint();
        game.ui.setVisible(true);

        game.enterPlayers();

        while (game.isRunning()){
            currentPlayer = game.playerIterator.next();
            game.playTurn(currentPlayer);
        }
    }
}