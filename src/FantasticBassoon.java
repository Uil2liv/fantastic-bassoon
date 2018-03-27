import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.MutableTreeNode;
import java.net.URL;
import java.util.Vector;

public class FantasticBassoon{
    static MainFrame mainFrame;
    static Searches searches = new Searches();
    static Vector<Provider> providers = new Vector<>();
    static {
        for (ProviderFactory.Providers provider : ProviderFactory.Providers.values()){
            providers.add(new Provider(provider));
        }
    }

    private static MutableTreeNode selectedSearch;
    static TreeSelectionListener treeSelectionListener = e -> {
        Object node;
        try {
            node = e.getNewLeadSelectionPath().getLastPathComponent();
        } catch (NullPointerException ex) {
            node = null;
        }
        changeSelectedSearch((MutableTreeNode) node);
    };

    private static void changeSelectedSearch(MutableTreeNode search) {
        selectedSearch = search;
        if (search instanceof Removable)
            Actions.removeSearchAction.setEnabled(true);
        else
            Actions.removeSearchAction.setEnabled(false);
    }

    public static void createAndShowNewSearchContext() {
        new NewSearchDialog();
    }

    public static void deleteSearch() {
        searches.remove(selectedSearch);
    }

    public static void closeApplication() {
        searches.save("searches.json");
        System.exit(0);
    }

    public static void refresh() {
        ((Refreshable)selectedSearch).refresh();
    }

    private static void createAndShowMainFrame() {
        mainFrame = new MainFrame("fantastic-bassoon");
    }

    public static void main(String[] args) {
        searches.load("searches.json");

        // Set path to Chrome Webdriver
        URL webDriverPath = FantasticBassoon.class.getResource("chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", webDriverPath.getPath());

        SwingUtilities.invokeLater(FantasticBassoon::createAndShowMainFrame);
    }

    interface Removable{}

    interface Refreshable {
        void refresh();
    }
}
