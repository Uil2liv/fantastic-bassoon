package app.ui.menu;

import app.ui.Actions;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class FileMenu extends JMenu {
    public FileMenu() {
        super("Fichier");

        // Define the mnemonic
        this.setMnemonic(KeyEvent.VK_F);

        // Define the content of the menu
        this.add(Actions.newSearchAction);
        this.add(Actions.saveAction);
        this.add(new JSeparator());
        this.add(Actions.quitAction);
    }
}
