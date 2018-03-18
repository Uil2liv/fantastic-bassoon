import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Menu Bar definition
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        JPanel panel = new JPanel(new BorderLayout());
        this.setContentPane(panel);

        // Tool Bar definition
        Tool_Bar toolBar = new Tool_Bar();
        panel.add(toolBar, BorderLayout.PAGE_START);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);


        SearchTree searchTree = new SearchTree();
        searchTree.setPreferredSize(new Dimension(200, 600));
        splitPane.setLeftComponent(searchTree);
        searchTree.expandRow(0);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(800,0));
        tabbedPane.addTab("Recherche 1", new JPanel());
        tabbedPane.addTab("Recherche 2", new JPanel());
        tabbedPane.addTab("Recherche 3", new JPanel());

        splitPane.setRightComponent(tabbedPane);

        panel.add(splitPane, BorderLayout.CENTER);

        // Attach Action Listener



        this.pack();
        this.setVisible(true);
    }
}
