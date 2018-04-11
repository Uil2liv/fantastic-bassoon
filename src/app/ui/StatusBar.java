package app.ui;

import app.FantasticBassoon;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.text.Format;
import java.text.NumberFormat;

public class StatusBar extends JPanel implements FantasticBassoon.SelectedSearchListener {
    JPanel nbAssetBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel nbAssetLabel = new JLabel("Nombre de biens : ");
    JLabel nbAsset = new JLabel();

    JPanel averageBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel averageLabel = new JLabel("Prix moyen : ");
    JLabel averagePrice = new JLabel();

    StatusBar() {
        super(new FlowLayout(FlowLayout.LEFT));

        this.nbAssetBox.setPreferredSize(new Dimension(150, 30));
        this.nbAssetBox.setAlignmentX(LEFT_ALIGNMENT);
        this.nbAssetBox.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(nbAssetBox);

        this.averageBox.setPreferredSize(new Dimension(200, 30));
        this.averageBox.setAlignmentX(LEFT_ALIGNMENT);
        this.averageBox.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(averageBox);

        FantasticBassoon.registerSelectionChangedListener(this);
    }

    void Update() {
        if (FantasticBassoon.getSelectedSearch().getAssetCount()>0) {
            nbAsset.setText(String.valueOf(FantasticBassoon.getSelectedSearch().getAssetCount()));
            String val = String.format("%,.2f €/m²", (float)FantasticBassoon.getSelectedSearch().getAveragePrice());
            averagePrice.setText(val);
            nbAssetBox.add(nbAssetLabel);
            nbAssetBox.add(nbAsset);
            averageBox.add(averageLabel);
            averageBox.add(averagePrice);
        } else {
            nbAssetBox.remove(nbAssetLabel);
            nbAssetBox.remove(nbAsset);
            averageBox.remove(averageLabel);
            averageBox.remove(averagePrice);
        }

        this.repaint();
        this.revalidate();
    }

/*
    @Override
    public Dimension getPreferredSize() {
        Dimension preferedSize = super.getPreferredSize();

        preferedSize.height = 20;

        return preferedSize;
    }
*/

    // Implements SelectedSearchListener
    @Override
    public void SelectedSearchChanged() {
        Update();
    }
}
