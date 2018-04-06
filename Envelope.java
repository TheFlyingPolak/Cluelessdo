import java.util.Random;

public class Envelope{
    private Card weapon;
    private Card murderer;
    private Card location;

    public Envelope(){
        weapon = new Card(getRandomWeapon().toString());
        murderer = new Card(getRandomCharacter().toString());
        location = new Card(getRandomRoom().toString());
    }

    private WeaponTypes getRandomWeapon(){
        Random random = new Random();
        return WeaponTypes.values()[random.nextInt(WeaponTypes.values().length)];
    }

    private CharacterNames getRandomCharacter(){
        Random random = new Random();
        return CharacterNames.values()[random.nextInt(CharacterNames.values().length)];
    }

    private RoomType getRandomRoom(){
        Random random = new Random();
        RoomType room = null;
        while (room == null || room == RoomType.NO_ROOM || room == RoomType.CORRIDOR || room == RoomType.CELLAR){
            room = RoomType.values()[random.nextInt(RoomType.values().length)];
        }
        return room;
    }

    public Card getMurderer() {
        return murderer;
    }

    public Card getWeapon() {
        return weapon;
    }

    public Card getLocation() {
        return location;
    }
}
