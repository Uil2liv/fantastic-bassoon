import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class SearchTree extends JTree {
    DefaultMutableTreeNode root;
    public SearchTree() {
        super();
        this.root = new DefaultMutableTreeNode("Mes recherches");
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        model.setRoot(this.root);
    }

    public void addSearch(String search) {
        this.root.add(new DefaultMutableTreeNode(search));
    }
}
