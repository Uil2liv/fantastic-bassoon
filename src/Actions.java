import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

public class Actions {
    static AbstractAction newSearchAction = createAction("New", "Nouvelle Recherche...",
            "Créer une nouvelle recherche", FantasticBassoon::createAndShowNewSearchContext,
            KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), KeyEvent.VK_N);
    static AbstractAction quitAction = createAction("Stop", "Quitter",
            "Quitter l'application", FantasticBassoon::closeApplication,
            KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK), KeyEvent.VK_Q);
    static AbstractAction removeSearchAction = createAction("Delete", "Supprimer",
            "Supprimer la recherche sélectionnée", FantasticBassoon::deleteSearch,
            KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE), KeyEvent.VK_S);

    static private AbstractAction createAction(String icon, String altText, String toolTip, Runnable func,
                                               KeyStroke shortcut, int mnemonic){
        URL smallIconURL = Actions.class.getResource("toolbarButtonGraphics/general/" + icon + "16.gif");
        URL largeIconURL = Actions.class.getResource("toolbarButtonGraphics/general/" + icon + "24.gif");

        AbstractAction action = new AbstractAction(altText, new ImageIcon(smallIconURL)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                func.run();
            }
        };
        action.putValue(Action.LARGE_ICON_KEY, new ImageIcon(largeIconURL));
        action.putValue(Action.SHORT_DESCRIPTION, toolTip);
        action.putValue(Action.ACCELERATOR_KEY, shortcut);
        action.putValue(Action.MNEMONIC_KEY, mnemonic);

        return action;
    }
}
