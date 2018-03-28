import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class AssetTableModel extends DefaultTableModel {
    AssetTableModel() {
        super();
        FantasticBassoon.registerSelectionChangedListener(this);
    }


    @Override
    public int getRowCount() {
        return FantasticBassoon.getSelectedSearch().getAssetCount();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] columns = {"Titre", "Prix", "Date"};
        return columns[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ad.AdField key = null;

        switch (columnIndex) {
            case 0:
                key = Ad.AdField.Title;
                break;
            case 1:
                key = Ad.AdField.Price;
                break;
            case 2:
                key = Ad.AdField.Date;
                break;
        }

        return FantasticBassoon.getSelectedSearch().getValue(rowIndex, key);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

/*
    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
*/
}
