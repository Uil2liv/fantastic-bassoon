import javax.swing.*;

public class Tool_Bar extends JToolBar{
    public Tool_Bar() {
        super();

        this.add(Actions.newSearchAction);
    }
}