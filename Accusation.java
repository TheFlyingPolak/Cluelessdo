public class Accusation {

    String suspect;
    String weapon;
    String room;

    public Accusation(){
        suspect = "";
        weapon = "";
        room = "";
    }

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

        cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "weapons");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        ui.getInfo().addText("Now please enter the weapon you believe " + suspect + " used, your options are:");
        cardPanel.showPanel();

        for (int j = 0; j < 6; j++){
            ui.getInfo().addText(WEAPON_NAMES[j].toString().substring(0,1) + WEAPON_NAMES[j].toString().substring(1).toLowerCase());
        }
        weapon = ui.getCmd().getCommand();
        cardPanel.removePanel();

        cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "rooms");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        ui.getInfo().addText("Enter the room you think the murder occurred in");
        cardPanel.showPanel();

        final String[] rooms = {"Monica + Chandlers Kitchen", "Monica + Chandlers Living Room", "Rachels Office",
                "Central Perk", "Geller Household", "Joeys Kitchen", "Joeys Living Room", "Phoebes Apartment", "Allesandros"};
        for (int j = 0; j < 9; j++){
            ui.getInfo().addText(rooms[j]);
        }
        room = ui.getCmd().getCommand();
        cardPanel.removePanel();
        ui.getLayers().remove(cardPanel);
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
