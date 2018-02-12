/*
 * The Character class is a subclass of Token and specifies the name of the character token.
 *
 * @author Jakub Gajewski
 */

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Character extends Token{
    private CharacterNames name;

    public Character(Tile currentTile, CharacterNames name){
        super(currentTile);
        this.name = name;
    }

    public CharacterNames getName(){
        return name;
    }
}