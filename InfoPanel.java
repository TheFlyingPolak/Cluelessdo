import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
 * This class displays information about the game, the current activity of the game
 */
public class InfoPanel extends JScrollPane {
    JTextArea text = new JTextArea(); // JTextArea to display the information

    // Constructor
    InfoPanel() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // have a vertical scroll bar if required
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // never have a horizontal scroll bar
        getViewport().setPreferredSize(new Dimension(300, 300)); // set the prefered dimensions of the JScrollPane viewport

        text.setMinimumSize(new Dimension(300, 300)); // set the prefered dimensions of the text object
        text.append("Information\n"); // add text to the text object
        text.setEditable(false); // make the text from the text object as non editable by the user
        text.setLineWrap(true); // go to the next line if text is too long
        text.setWrapStyleWord(true); // do not split a word in half to go to the next line, move whole words

        getViewport().add(text); // add text object to the JScrollPane viewport
    }

    // Method to add text to the text object
    public void append(String str) {
        text.append(str);
    }

    // method to return the text object object
    public JTextArea getText() {
        return text;
    }
}