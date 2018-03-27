import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Vector;

public class Search implements MutableTreeNode, FantasticBassoon.Removable, FantasticBassoon.Refreshable{
    private Query query;
    public Query getQuery() {
        return query;
    }
    public void setQuery(Query query) {
        this.query = query;
    }

    private Vector<Asset> assets = new Vector<>();

    public Search() { }
    public Search(Query query){
        this.query = query;
    }

    public String toString() {
        return this.query.toString();
    }

    // Implements MutableTreeNode
    private MutableTreeNode parent;

    @Override
    public void insert(MutableTreeNode child, int index) {

    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void remove(MutableTreeNode node) {

    }

    @Override
    public void setUserObject(Object object) {

    }

    @Override
    public void removeFromParent() {
        parent.remove(this);
    }

    @JsonIgnore
    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @JsonIgnore
    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return -1;
    }

    @JsonIgnore
    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration children() {
        return null;
    }

    // Implements Refreshable
    public void refresh() {
        for (Provider provider : FantasticBassoon.providers) {
            assets.addAll(provider.search(query));
        }
    }
}
