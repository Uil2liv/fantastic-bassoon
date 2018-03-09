import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        SearchTree searchTree = new SearchTree();
        this.getContentPane().add(searchTree);
        searchTree.addSearch("Recherche 1");
        searchTree.addSearch("Recherche 2");
        searchTree.addSearch("Recherche 3");

/*
        JLabel label = new JLabel("Hello, world!");
        this.getContentPane().add(label);
*/

        this.pack();
        this.setVisible(true);
    }
}
