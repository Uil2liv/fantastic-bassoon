import javax.swing.*;
import java.util.ArrayList;

public class FantasticBassoon {
    static MainFrame mainFrame;
    static Searches searches = new Searches();

    public static void createAndShowNewSearchContext() {
        new NewSearchDialog();
    }

    public static void deleteSearch() {
        System.out.println("On va supprimer une recherche.");
    }

    public static void closeApplication() {
        System.exit(0);
    }

    private static void createAndShowMainFrame() {
        mainFrame = new MainFrame("fantastic-bassoon");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowMainFrame();
            }
        });
    }

}
