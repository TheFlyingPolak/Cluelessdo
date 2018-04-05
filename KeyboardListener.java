//import javafx.scene.web.HTMLEditorSkin;

import java.awt.event.*;
import java.util.LinkedList;

/*
 * The class KeyboardListener allows the user to issue game commands by pressing keys on the keyboard
 */

public class KeyboardListener implements KeyListener{
    private CmdPanel cmdPanel;

    public KeyboardListener(CmdPanel cmdPanel){
        this.cmdPanel = cmdPanel;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        System.out.println("key pressed: " + key);
        String command;
            switch (key) {
                case KeyEvent.VK_UP:
                    command = "u";
                    break;
                case KeyEvent.VK_DOWN:
                    command = "d";
                    break;
                case KeyEvent.VK_LEFT:
                    command = "l";
                    break;
                case KeyEvent.VK_RIGHT:
                    command = "r";
                    break;
                case KeyEvent.VK_R:
                    command = "roll";
                    break;
                case KeyEvent.VK_D:
                    command = "done";
                    break;
                case KeyEvent.VK_P:
                    command = "passage";
                    break;
                case KeyEvent.VK_N:
                    command = "notes";
                    break;
                case KeyEvent.VK_1:
                    command = "1";
                    break;
                case KeyEvent.VK_2:
                    command = "2";
                    break;
                case KeyEvent.VK_3:
                    command = "3";
                    break;
                case KeyEvent.VK_4:
                    command = "4";
                    break;
                default:
                    command = "";
        }
        cmdPanel.addCommand(command);
    }

    public void keyReleased(KeyEvent e){
        /** Do nothing */
    }

    public void keyTyped(KeyEvent e){
        /** Do nothing */
    }
}
