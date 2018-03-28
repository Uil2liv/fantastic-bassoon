package app.ui.menu;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        this.add(new FileMenu());
        this.add(new EditMenu());
    }
}
