import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.util.Vector;

public class SearchTree extends JTree {


    private MutableTreeNode root;
    public SearchTree() {
        super();
        this.setEditable(true);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);
        this.root = FantasticBassoon.searches;

        this.addTreeSelectionListener(FantasticBassoon.treeSelectionListener);

        DefaultTreeModel model = (DefaultTreeModel) getModel();
        FantasticBassoon.searches.registerElementAddedListener(model);
        FantasticBassoon.searches.registerElementRemovedListener(model);
        model.setRoot(root);
    }

/*
    public void addSearch(String search) {
        this.root.add(new MutableTreeNode(search));
    }
*/
}
