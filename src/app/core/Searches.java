package app.core;

import app.core.Search;
import app.core.common.Ad;
import app.ui.tree.SearchTreeItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class Searches implements SearchTreeItem {
    private Vector<SearchTreeItem> elements = new Vector<>();
    private Vector<DefaultTreeModel> elementAddedListeners = new Vector<>();
    private Vector<DefaultTreeModel> elementRemovedListeners = new Vector<>();

    public void load(String path){
        File searchFile = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.elements = mapper.readValue(searchFile, new TypeReference<Vector<Search>>(){});
        } catch (FileNotFoundException e) {
            System.out.println("Aucune recherche précédente n'a été trouvée.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String path) {
        File searchFile = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(searchFile, this.elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Search search){
        elements.add(search);
        this.notifyElementAdded(search);
    }

    public void refresh() {
        // TODO Implement app.core.Search for all items in elements
    }

    private void notifyElementAdded(Search search){
        int index = this.getIndex(search);
        this.elementAddedListeners.forEach(listener -> listener.nodesWereInserted(this, new int[] {index}));
    }

    public void registerElementAddedListener(DefaultTreeModel listener) {
        this.elementAddedListeners.add(listener);
    }

    public void unregisterElementAddedListener(DefaultTreeModel listener) {
        this.elementAddedListeners.remove(listener);
    }

    public void registerElementRemovedListener(DefaultTreeModel listener){
        this.elementRemovedListeners.add(listener);
    }

    public void unregisterElementRemovedListener(DefaultTreeModel listener){
        this.elementRemovedListeners.remove(listener);
    }

    private void notifyElementRemoved(int index, MutableTreeNode q){
        this.elementRemovedListeners.forEach(listener -> listener.nodesWereRemoved(this, new int[] {index}, new MutableTreeNode[] {q}));
    }

    @Override
    public String toString() {
        return "Mes recherches";
    }

    // Implements app.ui.tree.SearchTreeItem
    @Override
    public int getAssetCount(){
        return 0;
    }

    @Override
    @JsonIgnore
    public int getAveragePrice() { return 0; }

    @Override
    public Object getValue(int i, Ad.AdField key) {
        return null;
    }

    @Override
    public Asset getAsset(int i) { return null; }

    // MutableTreeNode Implementation
    @Override
    public void insert(MutableTreeNode child, int index) {
        this.elements.insertElementAt((SearchTreeItem) child, index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        int index = this.getIndex(node);
        this.elements.removeElement(node);
        notifyElementRemoved(index, node);
    }

    @Override
    public void remove(int index){
        this.elements.remove(index);
    }

    @Override
    public void setUserObject(Object object) {

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
