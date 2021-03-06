package app;

import app.ui.table.AssetTable;
import app.ui.table.AssetTableModel;
import app.ui.tree.SearchTreeItem;
import app.ui.*;
import app.core.common.*;
import app.core.*;
import jdk.internal.org.objectweb.asm.TypeReference;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Arrays;
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
    private static Selectable[] selection;
    public static Asset[] getSelectedAssets() {
        Vector<Asset> assets = new Vector<>();

        for (Selectable sel : selection){
            if (sel instanceof Asset)
                assets.add((Asset)sel);
        }

        if (assets.size() > 0)
            return assets.toArray(new Asset[]{});
        else
            return null;
    }

    public static void changeSelection(Selectable[] selection) {
        FantasticBassoon.selection = selection;
        if (selection.length == 1 && selection[0] instanceof SearchTreeItem)
            changeSelectedSearch((SearchTreeItem)selection[0]);

        if (selection.length > 0 && selection[0] instanceof Removable)
            Actions.removeSearchAction.setEnabled(true);
        else
            Actions.removeSearchAction.setEnabled(false);

        if (selection.length > 1 && selection[0] instanceof Mergeable )
            Actions.mergeAssets.setEnabled(true);
        else
            Actions.mergeAssets.setEnabled(false);

    }
    public static AssetTable.AssetSelectionListener assetSelectionListener = assets -> {
        changeSelection(assets);
    };
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
        changeSelection(new Selectable[] {(SearchTreeItem)node});
    };

    private static void changeSelectedSearch(SearchTreeItem search) {
        selectedSearch = search;
        notifySelectedSearchChanged();
    }

    private static void notifySelectedSearchChanged() {
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
        for (Selectable sel : selection)
            if (sel instanceof Removable)
                ((Removable)sel).remove();

        notifySelectedSearchChanged();
    }

    public static void closeApplication() {
        save();
        System.exit(0);
    }

    public static void mergeAssets() {
        Asset.merge(getSelectedAssets());
        notifySelectedSearchChanged();
    }

    public static void save() {
        searches.save("searches.json");
    }

    public static void refresh() {
        (selectedSearch).refresh();
        notifySelectedSearchChanged();
    }

    private static void createAndShowMainFrame() {
        mainFrame = new MainFrame("fantastic-bassoon");
    }

    public static void main(String[] args) {
        searches.load("searches.json");

        // Try to locate the Chrome Webdriver
        File webDriver = new File("chromedriver.exe");
        if (!webDriver.exists()) {
            try {
                InputStream deliveredWD = FantasticBassoon.class.getResourceAsStream("/chromedriver.exe");
                Files.copy(deliveredWD, webDriver.toPath());
            } catch (IOException e) {
                System.out.println("Impossible de créer le WebDriver.");
                FantasticBassoon.closeApplication();
            }
        }

        // Set path to Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", webDriver.getPath());

        SwingUtilities.invokeLater(FantasticBassoon::createAndShowMainFrame);
    }

    public interface Selectable {}

    public interface Removable {
        void remove();
    }

    public interface Mergeable {}

    public interface Refreshable {
        void refresh();
    }

    public interface SelectedSearchListener {
        void SelectedSearchChanged();
    }


}
