
package cse215project;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Cse215Project
{
    public static void main(String[] args) {
        // Run the user interface
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserInterface ui = new UserInterface();
                ui.setVisible(true);
            }
        });
    }
}

