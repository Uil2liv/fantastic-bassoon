package app.ui.tree;

import app.FantasticBassoon;
import app.core.Asset;
import app.core.common.Ad;

import javax.swing.tree.MutableTreeNode;

public interface SearchTreeItem extends MutableTreeNode, FantasticBassoon.Refreshable{
    int getAssetCount();
    int getAveragePrice();
    Object getValue(int i, Ad.AdField key);
    Asset getAsset(int i);
}
