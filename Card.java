/*
 * Class represents cards dealt to all players at the start of the game which contain characters/weapons/rooms
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class Card {
    private String name;

    public Card(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
