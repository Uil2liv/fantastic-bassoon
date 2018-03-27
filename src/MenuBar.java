import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        this.add(new FileMenu());
        this.add(new EditMenu());
    }
}
