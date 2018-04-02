package app.ui.table;

import app.FantasticBassoon;
import app.core.common.Ad;

import javax.swing.table.DefaultTableModel;
import java.util.Date;


public class AssetTableModel extends DefaultTableModel {
    public AssetTableModel() {
        super();
        FantasticBassoon.registerSelectionChangedListener(this);
    }


    @Override
    public int getRowCount() {
        try {
            return FantasticBassoon.getSelectedSearch().getAssetCount();
        } catch (NullPointerException e) {
            // TODO avoid null exception if possible.
            // Exception occurs when the root tree element is collapsed.
            System.out.println("la méthode getAssetCount a retourné NULL");
            //e.printStackTrace();
            return 0;
        }
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

        switch (columnIndex) {
            case 0: return String.class;
            case 1: return Integer.class;
            case 2: return Date.class;
            default: return null;
        }
        //return getValueAt(0, columnIndex).getClass();
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
}
