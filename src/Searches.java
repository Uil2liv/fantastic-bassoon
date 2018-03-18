import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Vector;

public class Searches implements MutableTreeNode{
    private Object userObject;
    private Vector<Query> elements = new Vector<>();
    private Vector<DefaultTreeModel> elementAddedListeners = new Vector<>();

    public void add(Query q){
        elements.add(q);
        this.notifyElementAdded(q);
    }

    private void notifyElementAdded(Query q){
        int index = this.getIndex(q);
        this.elementAddedListeners.forEach(listener -> listener.nodesWereInserted(this, new int[] {index}));
    }

    public void registerElementAddedListener(DefaultTreeModel listener) {
        this.elementAddedListeners.add(listener);
    }

    public void unregisterElementAddedListener(DefaultTreeModel listener) {
        this.elementAddedListeners.remove(listener);
    }

    @Override
    public String toString() {
        return "Mes recherches";
    }

    // MutableTreeNode Implementation
    @Override
    public void insert(MutableTreeNode child, int index) {
        this.elements.insertElementAt((Query) child, index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        this.elements.removeElement(node);
    }

    @Override
    public void remove(int index){
        this.elements.remove(index);
    }

    @Override
    public void setUserObject(Object object) {
        this.userObject = object;
    }

    @Override
    public void removeFromParent() {

    }

    @Override
    public void setParent(MutableTreeNode newParent) {

    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return this.elements.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.elements.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return this.elements.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return (Enumeration)this;
    }
}
