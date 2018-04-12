package app.core;

import app.FantasticBassoon;
import app.core.common.Ad;
import app.ui.tree.SearchTreeItem;
import app.core.common.Provider;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Vector;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
public class Search implements SearchTreeItem, FantasticBassoon.Removable {
    private Query query;
    public Query getQuery() {
        return query;
    }
    public void setQuery(Query query) {
        this.query = query;
    }

    Vector<Asset> assets = new Vector<>();

    public Search() { }
    public Search(Query query){
        this.query = query;
    }

    private void updateAssets(Vector<Ad> ads) {
        for (Ad ad : ads) {
            if (getAsset(ad) != null) {
                Asset a = getAsset(ad);
                a.update(ad);
            } else {
                Asset a = new Asset(ad);
                assets.add(a);
            }
        }
    }

    public Asset getAsset(Ad ad) {
        for (Asset a : assets) {
            if (a.getAd(ad) != null)
                return a;
        }
        return null;
    }

    @JsonIgnore
    public int getAveragePrice() {
        if (getAssetCount() > 0) {
            int sum = 0;

            for (Asset asset : this.assets) {
                sum += asset.getAveragePrice();
            }

            return sum / this.getAssetCount();
        } else {
            return 0;
        }
    }

    public String toString() {
        return this.query.toString();
    }

    // Implements app.ui.tree.SearchTreeItem
    @JsonIgnore
    @Override
    public int getAssetCount() {
        return this.assets.size();
    }

    @Override
    public Object getValue(int i, Ad.AdField key) {
        return this.assets.get(i).get(key);
    }

    @Override
    public Asset getAsset(int i) {
        return assets.get(i);
    }

    public void removeAsset(Asset asset) {
        assets.remove(asset);
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
        Vector<Ad> ads = new Vector<>();

        for (Provider provider : FantasticBassoon.providers) {
            ads.addAll(provider.search(query));
        }

        updateAssets(ads);
    }
}
