public class Accusation {

    String suspect;
    String weapon;
    String room;

    public Accusation(){
        suspect = "";
        weapon = "";
        room = "";
    }

<<<<<<< HEAD
    public void ask(UI ui, CharacterNames[] CHARACTER_NAMES, WeaponTypes[] WEAPON_NAMES){
        CardPanel cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "characters");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        ui.getInfo().addText("Firstly enter the name of the character you believe to be the murderer, your options are:");
        cardPanel.showPanel();

        for (int j = 0; j < 6; j++){
            ui.getInfo().addText(CHARACTER_NAMES[j].toString().substring(0,1) + CHARACTER_NAMES[j].toString().substring(1).toLowerCase());
        }
        suspect = ui.getCmd().getCommand();
        cardPanel.removePanel();
=======
    public void ask(UI ui, CharacterNames[] CHARACTER_NAMES, WeaponTypes[] WEAPON_NAMES, Player currentPlayer, Envelope envelope){
        ui.getInfo().addText("Firstly enter the name of the character you believe to be the murderer, your options are:");
        for (int j = 0; j < 6; j++) {
            ui.getInfo().addText(CHARACTER_NAMES[j].toString().substring(0, 1) + CHARACTER_NAMES[j].toString().substring(1).toLowerCase());
        }
        do {
            suspect = ui.getCmd().getCommand().toLowerCase();
            switch (suspect) {
                case "mustard":
                case "joey":
                    suspect = "joey";
                    break;
                case "scarlet":
                case "phoebe":
                    suspect = "phoebe";
                    break;
                case "white":
                case "monica":
                    suspect = "monica";
                    break;
                case "green":
                case "chandler":
                    suspect = "chandler";
                    break;
                case "plum":
                case "ross":
                    suspect = "ross";
                    break;
                case "peacock":
                case "rachel":
                    suspect = "rachel";
                    break;
                case "notes":
                    currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                    break;
                case "cheat":
                    ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                    break;
                case "help":
                    ui.getInfo().addText("Enter the player from the list above for an accusation");
                    break;
                default:
                    ui.getInfo().addText("That was an invalid entry, enter a player from the list above");
                    ui.getInfo().addText("(having trouble with spelling? try copy and pasting a name from above)");
                    suspect = null;
                    break;
            }
        } while (suspect == null); // while the player input was not a character
>>>>>>> origin/master

        cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "weapons");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        ui.getInfo().addText("Now please enter the weapon you believe " + suspect + " used, your options are:");
        cardPanel.showPanel();

        for (int j = 0; j < 6; j++){
            ui.getInfo().addText(WEAPON_NAMES[j].toString().substring(0,1) + WEAPON_NAMES[j].toString().substring(1).toLowerCase());
        }
<<<<<<< HEAD
        weapon = ui.getCmd().getCommand();
        cardPanel.removePanel();
=======
        do {
            weapon = ui.getCmd().getCommand().toLowerCase();
            switch (weapon) {
                case "rope":
                    break;
                case "dagger":
                    break;
                case "wrench":
                    break;
                case "pistol":
                case "gun":
                    weapon = "pistol";
                    break;
                case "candlestick":
                case "candle stick":
                    weapon="candlestick";
                    break;
                case "pipe":
                    break;
                case "notes":
                    currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                    break;
                case "cheat":
                    ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                    break;
                case "help":
                    ui.getInfo().addText("Select a weapon from the list above for the accusation!");
                    break;
                default:
                    ui.getInfo().addText("That was an invalid entry, enter a weapon from the list above for the questioning!");
                    ui.getInfo().addText("(having trouble with spelling? try copy and pasting a name from above)");
                    weapon = null;
                    break;
            }
        } while (weapon == null); // while the player input was not a weapon
>>>>>>> origin/master

        cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "rooms");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        ui.getInfo().addText("Enter the room you think the murder occurred in");
        cardPanel.showPanel();

        final String[] rooms = {"Monica + Chandlers Kitchen", "Monica + Chandlers Living Room", "Rachels Office",
                "Central Perk", "Geller Household", "Joeys Kitchen", "Joeys Living Room", "Phoebes Apartment", "Allesandros"};
        for (int j = 0; j < 9; j++){
            ui.getInfo().addText(rooms[j]);
        }
<<<<<<< HEAD
        room = ui.getCmd().getCommand();
        cardPanel.removePanel();
        ui.getLayers().remove(cardPanel);
=======
        do{
            room = ui.getCmd().getCommand().toLowerCase();
            switch (room){
                case "monica & chandlers kitchen":
                case "monica + chandlers kitchen":
                case "monica and chandlers kitchen":
                case "monica & chandler's kitchen":
                case "monica + chandler's kitchen":
                case "monica and chandler's kitchen":
                case "mc kitchen":
                case "mc_kitchen":
                    room = "monica + chandlers kitchen";
                    return;
                case "monica & chandlers living room":
                case "monica + chandlers living room":
                case "monica and chandlers living room":
                case "monica & chandler's living room":
                case "monica + chandler's living room":
                case "monica and chandler's living room":
                case "monica & chandlers livingroom":
                case "monica + chandlers livingroom":
                case "monica and chandlers livingroom":
                case "monica & chandler's livingroom":
                case "monica + chandler's livingroom":
                case "monica and chandler's livingroom":
                case "mc livingroom":
                case "mc_livingroom":
                    room = "monica + chandlers living room";
                    return;
                case "rachel's office":
                case "rachels office":
                case "r office":
                case "r_office":
                    room = "rachel's office";
                    return;
                case "central perk":
                case "centralperk":
                    room = "central perk";
                    return;
                case "the geller household":
                case "geller household":
                case "gellerhouse":
                    room = "geller household";
                    return;
                case "joey's kitchen":
                case "joeys kitchen":
                case "j kitchen":
                case "j_kitchen":
                    room = "joey's kitchen";
                    return;
                case "joey's living room":
                case "joeys living room":
                case "joey's livingroom":
                case "joeys livingroom":
                case "j livingroom":
                case "j_livingroom":
                    room = "joey's living room";
                    return;
                case "phoebe's apartment":
                case "phoebes apartment":
                case "p apartment":
                case "p_apartment":
                    room = "phoebe's apartment";
                    return;
                case "allesandros":
                case "alesandros":
                case "allesandro's":
                case "alesandro's":
                    room = "allesandros";
                    return;
                case "notes":
                    currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                    break;
                case "cheat":
                    ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                    break;
                case "help":
                    ui.getInfo().addText("Select a room from the list above for the accusation!");
                    break;
                default:
                    ui.getInfo().addText("That was an invalid entry, enter a room from the list above for the questioning!");
                    ui.getInfo().addText("(having trouble with spelling? try copy and pasting a name from above)");
                    room = null;
                    break;
            }

        }while (room == null);

>>>>>>> origin/master
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
