package app.ui.adview;

import app.core.Asset;
import app.core.common.Ad;
import app.core.common.ProviderFactory;
import app.ui.table.AssetTable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

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

                if (((Collection<String>)asset.get(Ad.AdField.Pictures)).size() > 0) {
                    // Display a picture preview
                    Preview preview = new Preview(((ArrayList<String>)asset.get(Ad.AdField.Pictures)).get(0));
                    this.insertComponent(preview);
                    doc.insertString(doc.getLength(), "\n", body);

                    // Display thumbnails
                    for (String imgPath : (ArrayList<String>) asset.get(Ad.AdField.Pictures)) {
                        Thumbnail thumbnail = new Thumbnail(imgPath);
                        this.insertComponent(thumbnail);
                        doc.insertString(doc.getLength(), " ", body);

                        // Add a listener to the thumbnails to display the picture in the preview
                        // when the thumbnail is clicked.
                        thumbnail.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                preview.setImage(((Thumbnail)e.getSource()).path);
                            }
                        });
                    }

                    // Display references
                    doc.insertString(doc.getLength(), "\nSources :", label);
                    for (Ad ad  : asset) {
                        doc.insertString(doc.getLength(), "\n" + ((ProviderFactory.Providers)ad.get(Ad.AdField.Provider)).getName() + " : ", body);
                        JLabel url = new JLabel();
                        url.setAlignmentY(0.85f);
                        url.setText("<html><a href=\"\">" + ad.get(Ad.AdField.URL) + "</a></html>");
                        url.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                    Desktop.getDesktop().browse(new URI((String)ad.get(Ad.AdField.URL)));
                                } catch (URISyntaxException | IOException exception) {
                                    exception.printStackTrace();
                                }
                            }
                        });
                        this.insertComponent(url);
                        //doc.insertString(doc.getLength(), (String)ad.get(Ad.AdField.URL), body);
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
