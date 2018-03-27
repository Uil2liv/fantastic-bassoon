import javax.swing.*;
import java.awt.event.KeyEvent;

public class EditMenu extends JMenu {
    public EditMenu() {
        super("Édition");

        // Define the mnemonic
        this.setMnemonic(KeyEvent.VK_E);

        // Define the content of the menu
        this.add(Actions.refreshSearchAction);
    }
}
