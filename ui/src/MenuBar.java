import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        this.add(new FileMenu());
    }
}
