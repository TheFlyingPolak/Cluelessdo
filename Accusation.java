public class Accusation {

    String suspect;
    String weapon;
    String room;
    UI ui;
    Player currentPlayer;
    CharacterNames[] CHARACTER_NAMES;
    WeaponTypes[] WEAPON_NAMES;

    public Accusation(){
        suspect = "";
        weapon = "";
        room = "";
    }

    public void ask(UI ui, CharacterNames[] CHARACTER_NAMES, WeaponTypes[] WEAPON_NAMES){
        ui.getInfo().addText("Firstly enter the name of the character you believe to be the murderer, your options are:");
        for (int j = 0; j < 6; j++){
            ui.getInfo().addText(CHARACTER_NAMES[j].toString().substring(0,1) + CHARACTER_NAMES[j].toString().substring(1).toLowerCase());
        }
        suspect = ui.getCmd().getCommand();

        ui.getInfo().addText("Now please enter the weapon you believe " + suspect + " used, your options are:");
        for (int j = 0; j < 6; j++){
            ui.getInfo().addText(WEAPON_NAMES[j].toString().substring(0,1) + WEAPON_NAMES[j].toString().substring(1).toLowerCase());
        }
        weapon = ui.getCmd().getCommand();

        ui.getInfo().addText("Enter the room you think the murder occurred in");
        final String[] rooms = {"Monica + Chandlers Kitchen", "Monica + Chandlers Living Room", "Rachels Office",
                "Central Perk", "Geller Household", "Joeys Kitchen", "Joeys Living Room", "Phoebes Apartment", "Allesandros"};
        for (int j = 0; j < 9; j++){
            ui.getInfo().addText(rooms[j]);
        }
        room = ui.getCmd().getCommand();
    }

    public boolean isCorrect(Envelope envelope){
        if((suspect.toLowerCase().equals(envelope.getMurderer().getName().toLowerCase())) && (weapon.toLowerCase().equals(envelope.getWeapon().getName().toLowerCase())) && (room.toLowerCase().equals(envelope.getLocation().getName().toLowerCase()))){
            return true;
        }

        else{
            return false;
        }
    }
}
