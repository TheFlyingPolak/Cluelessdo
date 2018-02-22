/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * The Player class is the class that contains the information of the
 * person playing the game.
 */
public class Player {
    //name of the human playing the game
    private String playerName;

    //the character token that the player has chosen to move around the room
    private Character playerToken;

    //boolean value that states whether or not it is the players turn
    private boolean live;

    //constructor of the player object
    public Player(String playerName, Character playerToken){
        this.playerName = playerName;
        this.playerToken = playerToken;
        this.live = false;
    }
}
