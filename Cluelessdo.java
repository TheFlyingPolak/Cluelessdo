
/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Cluelessdo {
    private final TokenController tokenPanel;
    private final DicePanel dicePanel;
    private UI ui;
    private CircularlyLinkedList<Player> players;
    private Iterator<Player> playerIterator;
    private int numberOfPlayers = 0;
    private boolean running;
    private ArrayList<Card> publicCards = new ArrayList<>();    // List of cards visible to all players. Initially contains all cards
    private final Envelope envelope = new Envelope();

    Cluelessdo() throws IOException {
        ui = new UI();
        tokenPanel = new TokenController(ui.getBoard().getMap(),ui.getBoard());     // Token drawing panel
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
    public boolean moveCharacter(Character playerToken, Direction dir, String playerName) {
        int returnCode = playerToken.movePlayer(dir, ui.getBoard().getMap());
        if (returnCode != 0) {
            playerName = playerName.substring(0, 1) + playerName.substring(1).toLowerCase(); // capitalise the first letter and set the rest to lower case
            String errorMessage = playerName + " cannot move " + dir.toString().toLowerCase() + ": "; // make error message
            switch(returnCode){
                case 1:
                    errorMessage += "Cannot move through a wall.";
                    break;
                case 2:
                    errorMessage += "Cannot move through a token.";
                    break;
                case 3:
                    errorMessage += "Cannot return to the room which you left in the same turn.";
                    break;
            }

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
        Room currRoom = ui.getBoard().getMap().getRoom(playerToken.getCurrentTile().getRoomType().ordinal()); // get the room that the player is currently in
        if (currRoom.hasSecretPasssage()) {
            Room nextRoom = currRoom.getSecretPassage(); // get the room that the secret passage brings players to
            playerToken.moveToken(nextRoom.addToken()); // move the player to the token in the room that the secret passage is connected to
            tokenPanel.repaint();
            return true; // successful
        } else {
            return false; // cannot move player
        }
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
            case "notes": return CommandTypes.NOTES;
            case "cheat": return CommandTypes.CHEAT;
            case "question": return CommandTypes.QUESTION;
            case "accuse" : return CommandTypes.ACCUSE;
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
            case "help":
                return CommandTypes.HELP;
            default:
                ui.getInfo().addText("Invalid command: \"" + command + "\"");
                return CommandTypes.INVALID;
        }
        return CommandTypes.INVALID;
    }

    public void dealCards(){
        /** Remove cards already present in the envelope */
        Predicate<Card> predicate = (Card c) -> c.getEnumName().equals(envelope.getLocation().getEnumName()) || c.getEnumName().equals(envelope.getMurderer().getEnumName()) || c.getEnumName().equals(envelope.getWeapon().getEnumName());
        publicCards.removeIf(predicate);

        /** Distribute public cards among players */
        int cardCount = 0;
        while (cardCount + numberOfPlayers <= publicCards.size()){
            for (int i = 0; i < players.getSize(); i++){
                Player player = playerIterator.next();
                Card card = publicCards.get(0);
                player.getCards().add(card);
                publicCards.remove(0);
                player.getPlayerNotes().getNoteItem(card.getEnumName()).setOwned();
            }
        }
        for (Card tmp: publicCards){
            for (int i = 0; i < players.getSize(); i++){
                Player player = playerIterator.next();
                player.getPlayerNotes().getNoteItem(tmp.getEnumName()).setShared();
            }
        }
    }

    /**
     * Method to be executed at the start of the game to initialise the list of human players who will play.
     */
    private void enterPlayers(){
        String commandLineInput;

        /* Ask for the number of players until the user enters a valid number */
        do{
            commandLineInput = ui.getCmd().getCommand();
            if (commandLineInput.equals("help")) { // if the player entered help
                ui.getInfo().addText("Enter the number of players, there must be at least 2 players and at most 6");
            } else {
                try {
                    numberOfPlayers = Integer.parseInt(commandLineInput);
                } catch (NumberFormatException e) {
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
            }
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

            while (commandLineInput.equals("help")) { // if the user enters help (ensure that if the user enters help again that the message displays)
                ui.getInfo().addText("Enter the name you want to give Player " + (i+1));
                commandLineInput = ui.getCmd().getCommand();
            }

            name = commandLineInput;

            /** Select playable character */
            ui.getInfo().addText("Who would you like to play as, " + name + "? Available characters:");
            final String[] names = {"Joey", "Monica", "Chandler", "Phoebe", "Rachel", "Ross"};
            for (int j = 0; j < 6; j++){
                ui.getInfo().addText(names[j]);
            }
            boolean loop = true;
            do{
                character = ui.getCmd().getCommand().toLowerCase();
                switch (character){
                    case "mustard":
                    case "joey":
                        if (Arrays.asList(characterNames).contains(CharacterNames.JOEY)) {
                            ui.getInfo().addText("Someone has already chosen Joey. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.JOEY;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.JOEY)));
                        loop = false;
                        break;
                    case "scarlet":
                    case "phoebe":
                        if (Arrays.asList(characterNames).contains(CharacterNames.PHOEBE)){
                            ui.getInfo().addText("Someone has already chosen Phoebe. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.PHOEBE;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.PHOEBE)));
                        loop = false;
                        break;
                    case "white":
                    case "monica":
                        if (Arrays.asList(characterNames).contains(CharacterNames.MONICA)){
                            ui.getInfo().addText("Someone has already chosen Monica. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.MONICA;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.MONICA)));
                        loop = false;
                        break;
                    case "green":
                    case "chandler":
                        if (Arrays.asList(characterNames).contains(CharacterNames.CHANDLER)){
                            ui.getInfo().addText("Someone has already chosen Chandler. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.CHANDLER;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.CHANDLER)));
                        loop = false;
                        break;
                    case "plum":
                    case "ross":
                        if (Arrays.asList(characterNames).contains(CharacterNames.ROSS)){
                            ui.getInfo().addText("Someone has already chosen Ross. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.ROSS;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.ROSS)));
                        loop = false;
                        break;
                    case "peacock":
                    case "rachel":
                        if (Arrays.asList(characterNames).contains(CharacterNames.RACHEL)){
                            ui.getInfo().addText("Someone has already chosen Rachel. Try again.");
                            break;
                        }
                        characterNames[i] = CharacterNames.RACHEL;
                        players.addLast(new Player(name, tokenPanel.getPlayerToken(CharacterNames.RACHEL)));
                        loop = false;
                        break;
                    case "help":
                        ui.getInfo().addText("Pick one of the characters listed above by entering their name");
                        break;
                    default:
                        ui.getInfo().addText(character + " is not a valid character in this game");
                }
            } while (loop);
        }

        // get the players to roll the dice and then order them in that order for them to play
        PlayerOrder playerOrder = new PlayerOrder(players, ui, dicePanel);
        players = playerOrder.playerStartOrder(players);

        // get the name of each of the players in the order that they are going to play
        String names = "";
        int playerNum = 0;
        for (Player player : players) {
            if (playerNum == players.getSize()-1) {
                names = names.substring(0, names.length()-2) + " and then " + player.getPlayerName();
            } else {
                names += player.getPlayerName() + ", ";
            }
            playerNum++;
        }

        ui.getInfo().addText("The player order is " + names + "\nType \"play\" to begin!");

        String command;
        do {
            command = ui.getCmd().getCommand();
            if (command.equals("help")) { // if the player enters help
                ui.getInfo().addText("You just need to enter \"play\" to start the game!");
            }
        } while (!command.equals("play"));

        ui.getInfo().clear();
        playerIterator = players.iterator();
    }

    public void playTurn(Player currentPlayer){
        CommandTypes command;
        int numberOfMoves = 0;
        boolean questionAsked = false;
        ui.getInfo().addText(currentPlayer.getPlayerName() + ", it's your turn! Type \"roll\" to roll the dice.");

        /** Execute dice roll and record the number on the dice */
        do{
            command = doCommand();
            if (command == CommandTypes.ROLL){
                if (dicePanel.isRunning()) {
                    synchronized (dicePanel) {
                        dicePanel.notify();
                    }
                }
                dicePanel.start();
                numberOfMoves = dicePanel.getTotalDiceNumber();
                ui.getInfo().addText("You rolled " + numberOfMoves);
                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() == RoomType.CORRIDOR) { // if the character of the player is in the corridor
                    ui.getInfo().addText("Enter 'u', 'd', 'l' or 'r' to move up, down, left or right respectively, \"pass\" to move through the secret passage (if possible), \"notes\" to look at your notes, \"done\" when you are finished your turn");
                }
            } else if (command == CommandTypes.NOTES) {
                currentPlayer.getPlayerNotes().showNotes();
            } else if (command == CommandTypes.HELP) { // if the player enters help
                ui.getInfo().addText("Enter \"roll\" to roll the dice or \"notes\" to display your notes");
            } else if (command == CommandTypes.CHEAT){
                ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
            } else {
                ui.getInfo().addText("Invalid command. Enter \"roll\" to roll the dice or \"notes\" to display your notes");
            }

        } while (command != CommandTypes.ROLL);

        /** Overall control over player action after the dice roll. Main loop repeats until numberOfMoves
         *  reaches 0 through exhaustion of moves or the user typing "done" */
        do{
            /** Ask player to end the turn once numberOfMoves == 0 and the loop has repeated */
            if (numberOfMoves == 0) {
                do{
                    /** If a player is in the cellar they make an accusation*/
                    if(currentPlayer.getPlayerToken().getCurrentTile().getRoomType() == RoomType.CELLAR){
                        do {
                            ui.getInfo().addText("You are about to make an accusation, to check your notes one more time enter \"notes\" otherwise enter \"accuse\"");
                            command = doCommand();
                        }while (command != CommandTypes.ACCUSE && command != CommandTypes.NOTES && command != CommandTypes.CHEAT);

                        if (command == CommandTypes.NOTES){
                            currentPlayer.getPlayerNotes().showNotes();
                        }
                        else if (command == CommandTypes.CHEAT){
                            ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                        }

                        ui.getInfo().addText("Firstly enter the name of the character you believe to be the murderer, your options are:");
                        final String[] names = {"Joey", "Monica", "Chandler", "Phoebe", "Rachel", "Ross"};
                        for (int j = 0; j < 6; j++){
                            ui.getInfo().addText(names[j]);
                        }
                        String suspect = ui.getCmd().getCommand();

                        ui.getInfo().addText("Now please enter the weapon you believe " + suspect + " used, your options are:");
                        final String[] weapons = {"Rope", "Dagger", "Wrench", "Pistol", "Candlestick", "Pipe"};
                        for (int j = 0; j < 6; j++){
                            ui.getInfo().addText(weapons[j]);
                        }
                        String weapon = ui.getCmd().getCommand();

                        ui.getInfo().addText("Enter the room you think the murder occurred in");
                        final String[] rooms = {"Monica + Chandlers Kitchen", "Monica + Chandlers Living Room", "Rachels Office",
                                "Central Perk", "Geller Household", "Joeys Kitchen", "Joeys Living Room", "Phoebes Apartment", "Allesandros"};
                        for (int j = 0; j < 9; j++){
                            ui.getInfo().addText(rooms[j]);
                        }
                        String room = ui.getCmd().getCommand();

                        if((suspect.toLowerCase().equals(envelope.getMurderer().getName().toLowerCase())) && (weapon.toLowerCase().equals(envelope.getWeapon().getName().toLowerCase())) && (room.toLowerCase().equals(envelope.getLocation().getName().toLowerCase()))){
                            ui.getInfo().addText("Congratulations you have won");
                            //something cool
                        }

                        else{
                            players.remove(currentPlayer);
                            ui.getInfo().addText(" Sorry that is incorrect, you are eliminated");
                            break;
                        }
                    }

                    if (command == CommandTypes.NOTES) {
                        currentPlayer.getPlayerNotes().showNotes();
                    }
                    else if (command == CommandTypes.CHEAT){
                        ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                    } else if (command == CommandTypes.QUESTION && currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR) {
                        if (questionAsked) {
                            ui.getInfo().addText("You have already questioned and you are out of moves! Enter \"done\' to end yout turn or \"notes\" to look at your notes.");
                        } else {
                            questionAsked = true;
                            question(currentPlayer);
                        }
                    } else if (command == CommandTypes.QUESTION) {
                        ui.getInfo().addText("You have already questioned! Enter \"done\" to end yout turn or \"notes\" to look at your notes.");
                    } else { // if the player enters help or if the wrong input is entered (same message is displayed)
                        ui.getInfo().addText("You are out of moves! Type " + (questionAsked || currentPlayer.getPlayerToken().getCurrentTile().getRoomType() == RoomType.CORRIDOR ? "" : "\"question\" to make a question ") + "\"done\" to end your turn or enter \"notes\" to look at your notes.");
                    }
                    command = doCommand();
                } while (command != CommandTypes.DONE);
                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR)
                    currentPlayer.getPlayerToken().setRoomLastOccupied(currentPlayer.getPlayerToken().getCurrentTile().getRoomType());
                else
                    currentPlayer.getPlayerToken().setRoomLastOccupied(null);
            }

            /** If player is in a room, ask player to choose an exit or use a secret passage */
            else if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR){
                ui.getInfo().addText("Select an exit labelled on the screen or type \"pass\" or \"passage\" to use secret passage");

                /** Collect information about the room the player is occupying */
                int numberOfRoomExits = ui.getBoard().getMap().getRoomByType(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()).getNumberOfDoors();
                Tile[] doors = new Tile[numberOfRoomExits];
                for (int i = 0; i < numberOfRoomExits; i++) {
                    doors[i] = ui.getBoard().getMap().getRoomByType(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()).getDoor(i);
                    ui.getInfo().addText("Door " + (i + 1));
                    ui.getBoard().addDoorToNumber(doors[i]);
                }
                ui.getBoard().repaint();

                /** Prompt user to select exit or secret passage */
                boolean loop = true;
                do{
                    String commandString = ui.getCmd().getCommand();
                    /** User selects secret passage */
                    if (commandString.equals("pass") || commandString.equals("passage")){
                        Room room = ui.getBoard().getMap().getRoom(currentPlayer.getPlayerToken().getCurrentTile().getRoomType().ordinal());
                        room.removeToken();
                        if (moveSecretPassage(currentPlayer.getPlayerToken())) {
                            numberOfMoves = 0;
                            currentPlayer.getPlayerToken().setRoomLastOccupied(currentPlayer.getPlayerToken().getCurrentTile().getRoomType());
                            loop = false;
                        }
                        else
                            ui.getInfo().addText("This room does not have a secret passage!");
                    } else if (command == CommandTypes.QUESTION && currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR) {
                        questionAsked = true;
                        question(currentPlayer);
                        numberOfMoves = 0;
                    } else if (command == CommandTypes.QUESTION) {
                        ui.getInfo().addText("You have already questioned! Enter \"done\' to end your turn or \"notes\" to look at your notes.");
                    } else if (commandString.equals("help")) { // if the user enters help
                        ui.getInfo().addText("Enter \"pass\" or \"passage\" to use the secret passage (if possible) or enter the door number you want to exit the room by");
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
                ui.getBoard().clearDoorsToNumber();
                ui.getBoard().repaint();
            }

            /** If player is not in a room, continue normal progression of movement */
            else{
                command = doCommand();
                switch (command) {
                    case MOVE_UP:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.UP, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case MOVE_DOWN:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.DOWN, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case MOVE_LEFT:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.LEFT, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case MOVE_RIGHT:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.RIGHT, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case NOTES:
                        currentPlayer.getPlayerNotes().showNotes();
                        break;
                    case CHEAT:
                        ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                        break;
                    case QUESTION:
                        ui.getInfo().addText("You cannot question right now, you must be in a room! Try another command");
                        break;
                    case PASSAGE:
                        ui.getInfo().addText("Cannot use secret passage: you are not in a room");
                        break;
                    case ACCUSE:
                        ui.getInfo().addText("You cannot accuse right now, you must be in the cellar! Try another command");
                        break;
                    case HELP:
                        ui.getInfo().addText("Enter \"u\" to move up, \"d\" to move down, \"l\" to move left, \"r\" to move right,\n\"passage\" to move through the secret passage,\n\"notes\" to display your notes,\n\"done\" When you are finished your turn");
                        break;
                    case DONE:
                        ui.getInfo().addText("You have ended your turn!");
                        numberOfMoves = 0;
                        currentPlayer.getPlayerToken().setRoomLastOccupied(null);
                        break;
                }
                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR) {
                    numberOfMoves = 0;
                    currentPlayer.getPlayerToken().setRoomLastOccupied(currentPlayer.getPlayerToken().getCurrentTile().getRoomType());
                }
            }

            if (dicePanel.isRunning()){
                synchronized (dicePanel){
                    dicePanel.notify();
                }
            }
        } while (command != CommandTypes.DONE);
    }


    public void question(Player currentPlayer) {
        Question question = new Question(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()); // player question

        question.ask(ui, currentPlayer); // get the murderer and the murder weapon that the player thinks killed gunther

        CharacterNames murderer = CharacterNames.getValue(question.getMurderer().getName()); // convert the card murderer string to CharacterNames
        WeaponTypes murderWeapon = WeaponTypes.getValue(question.getWeapon().getName()); // convert the card murderer string to WeaponTypes
        RoomType murderLocation = RoomType.getValue(question.getLocation().getName()); // convert the card murderer string to RoomType

        Room room = ui.getBoard().getMap().getRoom(murderLocation.ordinal()); // get the room that the current player is in/player thinks the murder happened

        Tile movePlayerTile = room.addToken(); // get the tile in the room that the potential murderer will be placed
        Tile moveWeaponTile = room.addToken(); // get the tile in the room that the potential murder weapon will be placed

        tokenPanel.getPlayerToken(murderer).moveToken(movePlayerTile); // move the potential murderer to their assigned tile in the room
        tokenPanel.getWeaponToken(murderWeapon).moveToken(moveWeaponTile); // move the potential murder weapon to its assigned tile in the room
        tokenPanel.repaint();

        String questioningResult = "";

        boolean tokenMatch = false; // boolean of whether the user inputs either done (i.e. doesnt have any of the tokens) or one of the token names that they have

        //loop through the other players asking whether they have one of the tokens or not
        for (Player player : players) {
            if (!currentPlayer.getPlayerName().equals(player.getPlayerName())) { // if not the current player
                ui.getInfo().clear(); // clear the info panel
                ui.getInfo().addText("confirm that " + player.getPlayerName() + " is playing now by entering y/n"); // prompt

                String userInput = ui.getCmd().getCommand(); // read input
                while (userInput.toLowerCase().equals("n") || userInput.toLowerCase().equals("no")) {  // ensure that the right player is playing at that moment, if not loop
                    ui.getInfo().addText("Cannot continue until " + player.getPlayerName() + " is playing now enter y/n"); // prompt
                    userInput = ui.getCmd().getCommand(); // read input
                }

                // prompt
                ui.getInfo().addText("if you have the character, weapon and/or the room below:\n" + question.getMurderer().getName() + "\n" + question.getLocation().getName() + "\n" + question.getWeapon().getName() + "\nenter the name of the one that you have. Enter \"done\" if you don't have any of them. If you have more than one just enter one of them.");

                boolean canContinue = false; //
                do {
                    String input = ui.getCmd().getCommand();
                    System.out.println("AGHH: " + input + " " + CharacterNames.getValue(input) + ", " + RoomType.getValue(input) + ", " + WeaponTypes.getValue(input));

                    if (input.equals("done")) {
                        ui.getInfo().addText("Ok!"); // text to user
                        canContinue = true; // boolean to exit loop
                    } else if (input.equals("notes")) {
                        currentPlayer.getPlayerNotes().showNotes(); // display players notes
                    } else if (input.equals("help")) {
                        ui.getInfo().addText("Enter \"done\" if you dont have either the character, room or weapon, the name of the character, room or weapon that you have (pick one if you've more than one), \"notes\" to view youre notes");
                    } else if (murderer == CharacterNames.getValue(input)) { // player has the potential murderer
                        currentPlayer.getPlayerNotes().setPlayerChecked(question.getMurderer().getEnumName());
                        canContinue = true; // boolean to exit loop
                        questioningResult = player.getPlayerName() + " has " + question.getMurderer().getName() + "."; // create response to current player questioning
                    } else if (murderLocation == RoomType.getValue(input)) { // player has the potential murder location
                        currentPlayer.getPlayerNotes().setRoomChecked(question.getLocation().getEnumName());
                        canContinue = true; // boolean to exit loop
                        questioningResult = player.getPlayerName() + " has " + question.getLocation().getName() + "."; // create response to current player questioning
                    } else if (murderWeapon == WeaponTypes.getValue(input)) { // player has the potential murder weapon
                        currentPlayer.getPlayerNotes().setWeaponChecked(question.getWeapon().getEnumName());
                        canContinue = true; // boolean to exit loop
                        questioningResult = player.getPlayerName() + " has " + question.getWeapon().getName() + "."; // create response to current player questioning
                    } else {
                        ui.getInfo().addText("That's not a valid entry, try again!"); // inform user of invalid input
                    }
                } while (!canContinue); // while the player has not entered the tokens that they have out of the list or entered done
            }
        }

        if (questioningResult.equals("")) { // if nobody has any of the tokens
            questioningResult = "Nobody had the character, weapon or the room!"; // create response to current player questioning
        }

        ui.getInfo().clear(); // clear the info panel
        ui.getInfo().addText("confirm that " + currentPlayer.getPlayerName() + " is playing now by entering y/n"); // prompt
        String input = ui.getCmd().getCommand(); // read input
        while (input.equals("no") || input.equals("n")) {  // ensure that the right player is playing at that moment, if not loop
            ui.getInfo().addText("you cannot continue until " + currentPlayer.getPlayerName() + " is playing now by entering y/n"); // prompt
            input = ui.getCmd().getCommand(); // read input
        }

        ui.getInfo().addText(questioningResult + "\nEnter \"done\" to end yout turn or \"notes\" to look at your notes."); // give current player results from questioning and possible actions
    }

    public void playMusic(String path){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception exception)
        {
        }
    }

    public static void main(String[] args) throws IOException {
        Cluelessdo game = new Cluelessdo();
        //game.playMusic("Friends.wav");
        Player currentPlayer;
        game.tokenPanel.repaint();
        game.ui.setVisible(true);

        game.enterPlayers();
        CardGenerator.generate(game.publicCards);
        game.dealCards();

        /** List all cards for debugging purposes */
        System.out.println("Envelope: " + game.envelope.getLocation() + ", " + game.envelope.getMurderer() + ", " + game.envelope.getWeapon());
        for (Player player: game.players){
            System.out.print(player.getPlayerName() + ": ");
            for (Card card: player.getCards()){
                System.out.print(card + ", ");
            }
            System.out.println();
        }
        System.out.print("Leftover: ");
        for (Card card: game.publicCards){
            System.out.print(card + ", ");
        }
        System.out.println();
        /** Debugging end */

        while (game.isRunning()){
            currentPlayer = game.playerIterator.next();
            if (game.players.getSize()==1) {
                game.ui.getInfo().addText("Congratulations " + currentPlayer.getPlayerName() + ", you are the champion!!!");
                //do something to show victory
                break;
            }
            game.playTurn(currentPlayer);
        }
    }
}
