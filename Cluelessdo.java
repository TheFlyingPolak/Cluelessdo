
/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

import java.io.IOException;

public class Cluelessdo {
    TokenController tokenPanel;
    UI ui;

    Cluelessdo() throws IOException {
        ui = new UI();
        tokenPanel = new TokenController(ui.getBoard());     // Token drawing panel

        ui.getLayers().add(tokenPanel, new Integer(2));
    }

    /*
        checks to see if the move is valid, if valid moves the player, if not prints error message onto info panel
        @return returns false if move is illegal, true if move has been made successfully
     */
    public boolean movePlayer(int playerIndex, Direction dir) {
        if (!tokenPanel.getPlayerTokens().get(playerIndex).moveToken(dir, ui.getBoard())) { // move the player and check if not successful
            String playerName = tokenPanel.getPlayerTokens().get(playerIndex).getName().toString(); // get the players name
            playerName = playerName.substring(0, 1) + playerName.substring(1).toLowerCase(); // capitalise the first letter and set the rest to lower case
            String errorMessage = playerName + " cannot move " + dir.toString().toLowerCase(); // make error message
            ui.getInfo().append(errorMessage); // add error message to info panel
            return false; // move not successful
        }
        return true; // move successful
    }

    public static void main(String[] args) throws IOException {
        Cluelessdo game = new Cluelessdo();

        // Test code to show how to move a player
        int rachel = 1;
        game.movePlayer(rachel, Direction.LEFT);

        // Test code to show how to move 2 players into a room player
        int monica = 2;
        int joey = 3;
        Direction dir = Direction.UP;
        game.movePlayer(monica, dir);
        game.tokenPanel.getPlayerTokens().get(monica).moveToken(game.ui.getBoard().getTile(4, 7)); // place monica at door
        game.movePlayer(monica, dir); // move monica into room
        game.tokenPanel.getPlayerTokens().get(joey).moveToken(game.ui.getBoard().getTile(4, 7)); // place monica at door
        game.movePlayer(joey, dir); // move joey into room

        //SHowing code to move player out of room
        game.tokenPanel.getPlayerToken(monica).moveToken(game.ui.getBoard().getRoom(RoomType.KITCHEN.ordinal()).getDoor(0));

        game.tokenPanel.repaint();

        game.ui.setVisible(true);
    }
}
