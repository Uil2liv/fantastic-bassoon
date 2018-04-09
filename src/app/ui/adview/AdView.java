package app.ui.adview;

import app.core.Asset;
import app.core.common.Ad;
import app.ui.table.AssetTable;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

//public class AdView extends JTextPane implements AssetTable.AssetSelectionListener{
public class AdView extends JEditorPane implements AssetTable.AssetSelectionListener{
    Style body;
    Style headers;
    Style title;
    Style price;
    Style criteria;
    Style label;
    Style picture;

    public AdView() {
        super("text/html", null);

        this.setEditorKit(new HTMLEditorKit());
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


/*
        picture = sc.addStyle("picture", null);
        ImageIcon image = new ImageIcon("LeBonCoin\\1388631331\\95a2baf43e0a48e08ccd31ea7ca34b0a16091aa7.jpg");
        StyleConstants.setIcon(picture, image);
*/

        this.writeDocument(null);
    }

    void writeDocument(Asset asset) {
        HTMLDocument doc = (HTMLDocument)this.getDocument();

        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        Element[] roots = doc.getRootElements();
        Element body = roots[0].getElement(0);

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
                doc.insertString(doc.getLength(), asset.get(Ad.AdField.Description).toString(), this.body);

                this.setCaretPosition(0);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                File img = new File("LeBonCoin\\1388631331\\95a2baf43e0a48e08ccd31ea7ca34b0a16091aa7.jpg");
                doc.insertAfterStart(body, "<img style=\"width: 50;\"src=\""+ img.toURI().toString() + "\" />");

                doc.insertString(doc.getLength(), "Maison de blablablah" + "\n", title);
                doc.insertString(doc.getLength(), NumberFormat.getCurrencyInstance().format((long)123000), price);
            } catch (BadLocationException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
