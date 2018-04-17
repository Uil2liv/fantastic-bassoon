package app.ui;

import app.FantasticBassoon;
import app.ui.adview.AdView;
import app.ui.menu.MenuBar;
import app.ui.table.*;
import app.ui.tree.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FantasticBassoon.closeApplication();
            }
        });

        // Menu Bar definition
        app.ui.menu.MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        JPanel panel = new JPanel(new BorderLayout());
        this.setContentPane(panel);

        // Tool Bar definition
        Tool_Bar toolBar = new Tool_Bar();
        panel.add(toolBar, BorderLayout.PAGE_START);

        // Status Bar Definition
        StatusBar statusBar = new StatusBar();
        panel.add(statusBar, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Search Tree definition
        SearchTree searchTree = new SearchTree();
        searchTree.setPreferredSize(new Dimension(200, 600));
        splitPane.setLeftComponent(searchTree);
        searchTree.expandRow(0);

        JSplitPane contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        contentPane.setPreferredSize(new Dimension(800, 0));

        // Asset Table definition
        AssetTable assetsList = new AssetTable(new AssetTableModel());
        assetsList.getModel().addTableModelListener(assetsList);

        JScrollPane assetsListScroller = new JScrollPane(assetsList);
        assetsList.setFillsViewportHeight(true);
        assetsListScroller.setPreferredSize(new Dimension(0, 200));
        contentPane.setTopComponent(assetsListScroller);

        // Ad preview definition
        AdView adView = new AdView();
        assetsList.addAssetSelectionListener(adView);
        JScrollPane adViewScroller = new JScrollPane(adView);
        contentPane.setBottomComponent(adViewScroller);

        splitPane.setRightComponent(contentPane);

        panel.add(splitPane, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }
}
