import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileMenu extends JMenu {
    public FileMenu() {
        super("Fichier");
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(quitter);
    }
}
