package app.ui;

import app.FantasticBassoon;
import app.ui.menu.MenuBar;
import app.ui.table.AssetTable;

import javax.swing.*;
import javax.swing.table.TableColumn;
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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);


        SearchTree searchTree = new SearchTree();
        searchTree.setPreferredSize(new Dimension(200, 600));
        splitPane.setLeftComponent(searchTree);
        searchTree.expandRow(0);

        JSplitPane contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        contentPane.setPreferredSize(new Dimension(800, 0));


        AssetTable searchContent = new AssetTable(new AssetTableModel());
        searchContent.getModel().addTableModelListener(searchContent);
        searchContent.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        for (int i = 0; i < searchContent.getColumnCount(); i++) {
            TableColumn col = searchContent.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    col.setPreferredWidth(600);
                    break;
                case 1:
                    col.setPreferredWidth(100);
                    break;
                case 2:
                    col.setPreferredWidth(100);
                    break;
            }
        }

        JScrollPane scrollPane = new JScrollPane(searchContent);
        searchContent.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        contentPane.setTopComponent(scrollPane);

        JPanel emptyPanel = new JPanel();
        contentPane.setBottomComponent(emptyPanel);

        splitPane.setRightComponent(contentPane);

        panel.add(splitPane, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }
}
