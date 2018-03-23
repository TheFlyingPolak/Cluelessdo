/*
 *  Class that holds the info of all the cards that are to be used to be guessed
 *  and sets displays whether or not these cards are owned seen
 */
import javax.swing.*;

public class Notes {
    private NoteItem[] notePlayers = new NoteItem[6];
    private NoteItem[] noteWeapons = new NoteItem[6];
    private NoteItem[] noteRooms = new NoteItem[9];
    private JFrame notes;

    //constructor sets all cards as a blank char as they have not been seen yet
    Notes(){
        notePlayers[0]=new NoteItem("Ross", "ROSS", ' ');
        notePlayers[1]=new NoteItem("Phoebe", "PHOEBE",' ');
        notePlayers[2]=new NoteItem("Joey", "JOEY",' ');
        notePlayers[3]=new NoteItem("Monica", "MONICA", ' ');
        notePlayers[4]=new NoteItem("Rachel", "RACHEL", ' ');
        notePlayers[5]=new NoteItem("Chandler", "CHANDLER", ' ');

        noteWeapons[0]=new NoteItem("Pistol", "PISTOL", ' ');
        noteWeapons[1]=new NoteItem("Wrench", "WRENCH", ' ');
        noteWeapons[2]=new NoteItem("Dagger", "DAGGER", ' ');
        noteWeapons[3]=new NoteItem("Candlestick", "CANDLESTICK", ' ');
        noteWeapons[4]=new NoteItem("Rope", "ROPE", ' ');
        noteWeapons[5]=new NoteItem("Pipe", "PIPE", ' ');

        noteRooms[0]=new NoteItem("Monica + Chandler's Kitchen", "MC_KITCHEN", ' ');
        noteRooms[1]=new NoteItem("Central Perk", "CENTRALPERK", ' ');
        noteRooms[2]=new NoteItem("Joey's Kitchen", "J_KITCHEN", ' ');
        noteRooms[3]=new NoteItem("Monica & Chandler's Living Room", "MC_LIVINGROOM", ' ');
        noteRooms[4]=new NoteItem("Rachel's Office", "R_OFFICE", ' ');
        noteRooms[5]=new NoteItem("Geller Household", "GELLERHOUSE", ' ');
        noteRooms[6]=new NoteItem("Allesandro's", "ALLESANDROS", ' ');
        noteRooms[7]=new NoteItem("Phoebe's Apartment", "P_APARTMENT", ' ');
        noteRooms[8]=new NoteItem("Joey's Living Room", "J_LIVINGROOM", ' ');
    }

    //creates a jframe to display the notes
    public void showNotes(){
        notes = new JFrame("Notes");
        
        //create header for the suspect cards
        String x = "Person\t\t               Found\n\n";

        //add player name and whether or not they hae been checked off on the notes
        for(int i=0; i<6; i++){
            x += (notePlayers[i].getName() + "\t\t\t" + notePlayers[i].getChecked() + "\n");
        }

        //create header for the suspect weapon cards
        x += "\nWeapon\t\t               Found\n\n";

        //add weapon and whether or not they hae been checked off on the notes
        for(int i=0; i<6; i++){
            x += (noteWeapons[i].getName() + "\t\t\t" + noteWeapons[i].getChecked() + "\n");
        }

        //create header for the suspected room cards
        x += "\nRooms\t\t               Found\n\n";

        //add rooms and whether or not they hae been checked off on the notes
        for(int i=0; i<9; i++){
            if(noteRooms[i].getName().length()<16) {
                x += (noteRooms[i].name + "\t\t\t" + noteRooms[i].getChecked() + "\n");
            }
            else if(noteRooms[i].getName().length()>28){
                x += (noteRooms[i].name + "\t" + noteRooms[i].getChecked() + "\n");
            }
            else{
                x += (noteRooms[i].name + "\t\t" + noteRooms[i].getChecked() + "\n");
            }
        }

        //create an uneditable text area to display all of the info
        JTextArea textArea = new JTextArea(x);
        textArea.setEditable(false);
        notes.add(textArea);
        notes.pack();

        notes.setLocation(1000,0);
        notes.setSize(400, 500);
        notes.setResizable(false);
        notes.setVisible(true);
    }

    public NoteItem getNoteItem(String enumName) {
        for (int i = 0; i < notePlayers.length; i++){
            if (notePlayers[i].getEnumName().equals(enumName))
                return notePlayers[i];
        }
        for (int i = 0; i < noteRooms.length; i++){
            if (noteRooms[i].getEnumName().equals(enumName))
                return noteRooms[i];
        }
        for (int i = 0; i < noteWeapons.length; i++){
            if (noteWeapons[i].getEnumName().equals(enumName))
                return noteWeapons[i];
        }
        return null;
    }
}
