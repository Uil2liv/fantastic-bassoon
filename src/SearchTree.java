import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.util.Vector;

public class SearchTree extends JTree {
/*    class SearchTreeModel implements TreeModel {
        private Searches root;
        private Vector<TreeModelListener> SearchTreeModelListeners = new Vector<>();

        public SearchTreeModel(Searches root) {
            this.root = root;
        }

        @Override
        public Object getRoot() {
            return root;
        }

        @Override
        public Object getChild(Object parent, int index) {
            return parent.getChildAt(index);
        }

        @Override
        public int getChildCount(Object parent) {
            return parent.getChildCount();
        }

        @Override
        public boolean isLeaf(Object node) {
            return node.isLeaf();
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {

        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            return parent.getIndex(child);
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
            this.SearchTreeModelListeners.add(l);
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {

        }
    }*/

    private MutableTreeNode root;
    public SearchTree() {
        super();
        this.setEditable(true);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);
        this.root = FantasticBassoon.searches;
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        FantasticBassoon.searches.registerElementAddedListener(model);
        model.setRoot(root);
    }

/*
    public void addSearch(String search) {
        this.root.add(new MutableTreeNode(search));
    }
*/
}
