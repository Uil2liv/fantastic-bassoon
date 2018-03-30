package app.ui.adview;

import app.core.Asset;
import app.core.common.Ad;
import app.ui.table.AssetTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.*;
import java.awt.*;
import java.text.NumberFormat;

public class AdView extends JTextPane implements AssetTable.AssetSelectionListener{
    AttributeSet body;
    AttributeSet headers;
    AttributeSet title;
    AttributeSet price;

    public AdView() {
        super();

        this.setEditable(false);

        StyleContext sc = StyleContext.getDefaultStyleContext();
        body = sc.addAttribute(sc.getEmptySet(), StyleConstants.Family, "Calibri");
        body = sc.addAttribute(body, StyleConstants.Size, 12);

        price = sc.addAttribute(body, StyleConstants.Foreground, Color.darkGray);
        price = sc.addAttribute(price, StyleConstants.Bold, true);
        price = sc.addAttribute(price, StyleConstants.Size, 20);

        //AttributeSet headersSet = AttributeSet.NameAttribute
        headers = sc.addAttribute(sc.getEmptySet(), StyleConstants.Family, "Cambria");

        title = sc.addAttribute(headers, StyleConstants.Size, 24);

        this.writeDocument(null);
    }

    void writeDocument(Asset asset) {
        StyledDocument doc = this.getStyledDocument();

        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        if (asset != null) {
            // TODO Write the asset Document
            try {
                doc.insertString(doc.getLength(), asset.get(Ad.AdField.Title) + "\n", title);
                doc.insertString(doc.getLength(), NumberFormat.getCurrencyInstance().format(asset.get(Ad.AdField.Price)), price);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                doc.insertString(doc.getLength(), "Maison de blablablah" + "\n", title);
                doc.insertString(doc.getLength(), NumberFormat.getCurrencyInstance().format((long)123000), price);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    // Implements AssetSelectionListener

    @Override
    public void assetSelectionChanged(Asset a) {
        if (a != null) {
            writeDocument(a);
        }
    }
}
