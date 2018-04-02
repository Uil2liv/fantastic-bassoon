package app.ui;

import javax.swing.*;

public class Tool_Bar extends JToolBar{
    public Tool_Bar() {
        super();

        this.add(Actions.newSearchAction);
        this.add(Actions.removeSearchAction);
        this.add(Actions.refreshSearchAction);
    }
}