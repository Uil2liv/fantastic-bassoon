import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class Query implements MutableTreeNode{
    // Properties
    private int id;
    public void setId(int id){
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    private String name;
    public String getName() {
        return this.name;
    }

    private AssetType type;
    public AssetType getType(){
        return type;
    }

    private String location;
    public String getLocation(){
        return location;
    }

    private String zip;
    public String getZipCode(){
        return zip;
    }

    // Constructor
    public Query(String name, AssetType assetType, String location, String zipCode){
        this.name = name;
        this.type = assetType;
        this.location = location;
        this.zip = zipCode;
    }

    public String toString(){
        return this.name;
    }

    // MutableTreeNode Implementation
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

    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

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

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration children() {
        return null;
    }
}
