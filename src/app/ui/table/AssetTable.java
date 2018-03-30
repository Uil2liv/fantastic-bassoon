package app.ui.table;

import app.FantasticBassoon;
import app.core.Asset;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Currency;
import java.util.Vector;

public class AssetTable extends JTable {
    public AssetTable(TableModel t) {
        super(t);

        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn col = getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    col.setPreferredWidth(600);
                    break;
                case 1:
                    col.setPreferredWidth(100);
                    col.setCellRenderer(new CustomRenderer(NumberFormat.getCurrencyInstance()));
                    break;
                case 2:
                    col.setPreferredWidth(100);
                    col.setCellRenderer(new CustomRenderer(new SimpleDateFormat("dd/MM/YY hh:mm")));
                    break;
            }
        }
    }

    public interface AssetSelectionListener {
        void assetSelectionChanged(Asset a);
    }

    Vector<AssetSelectionListener> selectionListeners = new Vector<>();

    public void addAssetSelectionListener(AssetSelectionListener asl) {
        selectionListeners.add(asl);
    }
    public void removeAssetSelectionListener(AssetSelectionListener asl) {
        selectionListeners.remove(asl);
    }

    private void notifyAssetSelectionChanged(Asset a) {
        for (AssetSelectionListener asl : selectionListeners) {
            asl.assetSelectionChanged(a);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
        super.valueChanged(e);

        // Let know listeners that a new Asset is selected
        if (!e.getValueIsAdjusting()) {
            Asset a = FantasticBassoon.getSelectedSearch().getAsset(getSelectedRow());
            notifyAssetSelectionChanged(a);
        }
    }

    class CustomRenderer extends DefaultTableCellRenderer {
        private Format formatter;

        public CustomRenderer(Format format){
            this.formatter = format;
            this.setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public void setValue(Object value){
            setText((value == null) ? "" : formatter.format(value));
        }
    }


}
