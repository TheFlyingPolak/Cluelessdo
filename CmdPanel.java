import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
16310943 James Byrne
16314761 Jakub Gajewski
16305706 Mark Hartnett
 */

// allows user to input commands to the game
public class CmdPanel extends JPanel{
    private JTextField inputText; // JTextField for users to type text
    private InfoPanel info; // reference to the InfoPanel

    // Constructor
    CmdPanel(InfoPanel infoPanel) {
        inputText = new JTextField(); // create JTextField Object
        inputText.setColumns(55); // set the columns for the inputText object
        inputText.addActionListener(new Listener()); // add listner to inputText object

        info = infoPanel; // assign infoPanel to info

        add(inputText); // add inputText object to the class
    }

    // Listener
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String content = inputText.getText(); // assign text from inputText JTextField to a string
            info.append(content + "\n"); // add contents of inputText to infoPanel
            inputText.setText(""); // clear the text
            info.getText().select(Integer.MAX_VALUE, 0); // scroll vertical scrollbar to the bottom (so you can see current activity
        }
    }
}
