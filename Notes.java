/*
 *  Class that holds the info of all the cards that are to be used to be guessed
 *  and sets displays whether or not these cards are owned seen
 */
import javax.swing.*;

public class Notes {
    NoteItem[] notePlayers = new NoteItem[6];
    NoteItem[] noteWeapons = new NoteItem[6];
    NoteItem[] noteRooms = new NoteItem[9];
    JFrame notes;

    //constructor sets all cards as a blank char as they have not been seen yet
    Notes(){
        notePlayers[0]=new NoteItem("Ross",' ');
        notePlayers[1]=new NoteItem("Phoebe",' ');
        notePlayers[2]=new NoteItem("Joey",' ');
        notePlayers[3]=new NoteItem("Monica",' ');
        notePlayers[4]=new NoteItem("Rachel",' ');
        notePlayers[5]=new NoteItem("Chandler",' ');

        noteWeapons[0]=new NoteItem("Pistol",' ');
        noteWeapons[1]=new NoteItem("Wrench",' ');
        noteWeapons[2]=new NoteItem("Dagger",' ');
        noteWeapons[3]=new NoteItem("Candlestick",' ');
        noteWeapons[4]=new NoteItem("Rope",' ');
        noteWeapons[5]=new NoteItem("Pipe",' ');

        noteRooms[0]=new NoteItem("Monica + Chandler's Kitchen",' ');
        noteRooms[1]=new NoteItem("Central Perk",' ');
        noteRooms[2]=new NoteItem("Joey's Kitchen",' ');
        noteRooms[3]=new NoteItem("Monica & Chandler's Living Room",' ');
        noteRooms[4]=new NoteItem("Rachel's Office",' ');
        noteRooms[5]=new NoteItem("Geller Household",' ');
        noteRooms[6]=new NoteItem("Allesandros",' ');
        noteRooms[7]=new NoteItem("Phoebe's Apartment",' ');
        noteRooms[8]=new NoteItem("Joey's Living Room",' ');
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
        for(int i=0; i<6; i++){
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
        notes.setResizable(false);
        notes.setVisible(true);
    }

}
