/*
 * The Character class is a subclass of Token and specifies the name of the character token.
 *
 * @author Jakub Gajewski
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