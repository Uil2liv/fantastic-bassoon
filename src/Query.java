import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class Query implements MutableTreeNode, FantasticBassoon.Removable{
    // Properties
    private String name;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private AssetType type;
    public AssetType getType(){
        return type;
    }
    public void setType(AssetType type) {
        this.type = type;
    }

    private String location;
    public String getLocation(){
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    private String zip;
    public String getZip(){
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    private int minPrice;
    public int getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    private int maxPrice;
    public int getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    private int minArea;
    public int getMinArea() {
        return minArea;
    }
    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    private int maxArea;
    public int getMaxArea() {
        return maxArea;
    }
    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    private int minRoom;
    public int getMinRoom() {
        return minRoom;
    }
    public void setMinRoom(int minRoom) {
        this.minRoom = minRoom;
    }

    private int maxRoom;
    public int getMaxRoom() {
        return maxRoom;
    }
    public void setMaxRoom(int maxRoom) {
        this.maxRoom = maxRoom;
    }

    // Constructor
    public Query() {}

    public Query(String name, AssetType assetType, String location, String zipCode){
        setName(name);
        setType(assetType);
        setLocation(location);
        setZip(zipCode);
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

    @JsonProperty
    @Override
    public void setUserObject(Object object) {

    }

    @Override
    public void removeFromParent() {
        parent.remove(this);
    }

    @JsonProperty
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

    @JsonIgnore
    @Override
    public TreeNode getParent() {
        return parent;
    }

    @JsonIgnore
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

}
