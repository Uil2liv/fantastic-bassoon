package app.ui.adview;

import app.core.Asset;
import app.core.common.Ad;
import app.ui.table.AssetTable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class AdView extends JTextPane implements AssetTable.AssetSelectionListener, Scrollable{
    Style body;
    Style headers;
    Style title;
    Style price;
    Style criteria;
    Style label;

    public AdView() {
        super();
        this.setEditable(false);

        StyleContext sc = StyleContext.getDefaultStyleContext();

        body = sc.addStyle("test", null);
        body.addAttribute(StyleConstants.FontFamily, "Calibri");
        body.addAttribute(StyleConstants.Size, 12);

        price = sc.addStyle("price", body);
        price.addAttribute(StyleConstants.Foreground, Color.darkGray);
        price.addAttribute(StyleConstants.Bold, true);
        price.addAttribute(StyleConstants.Size, 18);

        criteria = sc.addStyle("criteria", body);

        label = sc.addStyle("label", criteria);
        label.addAttribute(StyleConstants.Bold, true);
        label.addAttribute(StyleConstants.Underline, true);

        headers = sc.addStyle("header", null);
        headers.addAttribute(StyleConstants.Family, "Cambria");

        title = sc.addStyle("title", headers);
        title.addAttribute(StyleConstants.Size, 24);

        this.writeDocument(null);
    }

    void writeDocument(Asset asset) {
        Document doc = this.getDocument();

        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        if (asset != null) {
            // TODO Display pictures if any
            try {

                doc.insertString(doc.getLength(), asset.get(Ad.AdField.Title) + "\n", title);
                if (asset.get(Ad.AdField.Area) != null)
                    doc.insertString(doc.getLength(), NumberFormat.getIntegerInstance().format(asset.get(Ad.AdField.Area)) + "m² - ", price);
                doc.insertString(doc.getLength(), NumberFormat.getCurrencyInstance().format(asset.get(Ad.AdField.Price)) + " ", price);
                if (asset.get(Ad.AdField.Area) != null)
                    doc.insertString(doc.getLength(), "(" + NumberFormat.getIntegerInstance().format(asset.getAveragePrice()) + "€/m²)\n",price);
                else
                    doc.insertString(doc.getLength(), "\n", price);

                doc.insertString(doc.getLength(), "Date de mise à jour :", label);
                doc.insertString(doc.getLength(), " " + SimpleDateFormat.getDateInstance().format(asset.get(Ad.AdField.Date)) + "\n",criteria);

                if (asset.get(Ad.AdField.NbRooms) != null) {
                    doc.insertString(doc.getLength(), "Nombre de pièces :", label);
                    doc.insertString(doc.getLength(), " " + NumberFormat.getIntegerInstance().format(asset.get(Ad.AdField.NbRooms)) + "\n", criteria);
                }

                if (asset.get(Ad.AdField.Energy) != null) {
                    doc.insertString(doc.getLength(), "Classe énergie :", label);
                    doc.insertString(doc.getLength(), " " + asset.get(Ad.AdField.Energy) + "\n", criteria);
                }

                if (asset.get(Ad.AdField.GHG) != null) {
                    doc.insertString(doc.getLength(), "Émission gaz à effet de serre :", label);
                    doc.insertString(doc.getLength(), " " + asset.get(Ad.AdField.GHG) + "\n", criteria);
                }

                doc.insertString(doc.getLength(), "\nDescription :\n", label);
                doc.insertString(doc.getLength(), asset.get(Ad.AdField.Description).toString() + "\n", this.body);

                for (String imgPath : (Collection<String>)asset.get(Ad.AdField.Pictures)) {
                    File imgFile = new File(imgPath);
                    try {
                        BufferedImage bi = ImageIO.read(imgFile);
                        float ar = (float)bi.getHeight()/(float)bi.getWidth();
                        int width = 200;
                        int height = (int)(width * ar);
                        JLabel imgLabel = new JLabel(new ImageIcon(bi.getScaledInstance(width, height, 0)));

                        // Set a margin with a compound border
                        Border border = imgLabel.getBorder();
                        Border margin = new EmptyBorder(10,10,10,10);
                        imgLabel.setBorder(new CompoundBorder(border, margin));

                        this.insertComponent(imgLabel);
                        doc.insertString(doc.getLength(), " ", body);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                this.setCaretPosition(0);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    // Implements AssetSelectionListener
    @Override
    public void assetSelectionChanged(Asset a) {
        if (a != null) {
            writeDocument(a);
        }
    }

    // Implements Scrollable
    @Override
    public boolean getScrollableTracksViewportWidth() {return true;}
}
