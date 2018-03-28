import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
        MenuBar menuBar = new MenuBar();
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
