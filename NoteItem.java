public class NoteItem {
    String name;
    char checked;

    //default constructor
    NoteItem(){
        name = "";
        checked = ' ';
    }

    //object constructor
    NoteItem(String name, char checked){
        this.name = name;
        this.checked = checked;
    }

    //accessor methods
    public String getName() {
        return name;
    }

    public char getChecked() {
        return checked;
    }

    //set the noteItem checked with a specified character
    public void setChecked(char checked) {
        this.checked = checked;
    }

    //if the player owns a specific card then the card is marked with an X
    public void isOwned(){
        setChecked('X');
    }

    //if a player has seen a specific card this card is marked with an A
    public void wasSeen(){
        setChecked('A');
    }
}
