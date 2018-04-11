package app;

import app.ui.table.AssetTableModel;
import app.ui.tree.SearchTreeItem;
import app.ui.*;
import app.core.common.*;
import app.core.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import java.net.URL;
import java.util.Vector;

public class FantasticBassoon{
    public static MainFrame mainFrame;
    public static Searches searches = new Searches();
    public static Vector<Provider> providers = new Vector<>();
    static {
        for (ProviderFactory.Providers provider : ProviderFactory.Providers.values()){
            providers.add(new Provider(provider));
        }
    }

    private static Vector<SelectedSearchListener> selectionChangedListeners = new Vector<>();

    private static SearchTreeItem selectedSearch = searches;
    public static SearchTreeItem getSelectedSearch() {
        return selectedSearch;
    }

    public static TreeSelectionListener treeSelectionListener = e -> {
        Object node;
        try {
            node = e.getNewLeadSelectionPath().getLastPathComponent();
        } catch (NullPointerException ex) {
            node = null;
        }
        changeSelectedSearch((SearchTreeItem) node);
    };

    private static void changeSelectedSearch(SearchTreeItem search) {
        selectedSearch = search;
        notifySelectionChanged();
        if (search instanceof Removable)
            Actions.removeSearchAction.setEnabled(true);
        else
            Actions.removeSearchAction.setEnabled(false);
    }

    private static void notifySelectionChanged() {
        for (SelectedSearchListener listener : selectionChangedListeners)
            listener.SelectedSearchChanged();
    }

    public static void registerSelectionChangedListener (SelectedSearchListener listener){
        selectionChangedListeners.add(listener);
    }

    public static void unregisterSelectionChangedListener(SelectedSearchListener listener) {
        selectionChangedListeners.remove(listener);
    }

    public static void createAndShowNewSearchContext() {
        new NewSearchDialog();
    }

    public static void deleteSearch() {
        searches.remove(selectedSearch);
    }

    public static void closeApplication() {
        save();
        System.exit(0);
    }

    public static void mergeAssets() {
        Asset.merge();
    }

    public static void save() {
        searches.save("searches.json");
    }

    public static void refresh() {
        ((Refreshable)selectedSearch).refresh();
        notifySelectionChanged();
    }

    private static void createAndShowMainFrame() {
        mainFrame = new MainFrame("fantastic-bassoon");
    }

    public static void main(String[] args) {
        searches.load("searches.json");

        // Set path to Chrome Webdriver
        URL webDriverPath = FantasticBassoon.class.getResource("/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", webDriverPath.getPath());

        SwingUtilities.invokeLater(FantasticBassoon::createAndShowMainFrame);
    }

    public interface Removable{}

    public interface Refreshable {
        void refresh();
    }

    public interface SelectedSearchListener {
        void SelectedSearchChanged();
    }
}
