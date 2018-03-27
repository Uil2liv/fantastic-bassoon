import sun.text.resources.es.JavaTimeSupplementary_es;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.geom.QuadCurve2D;

public class NewSearchDialog extends JDialog implements ActionListener{
    private JTextField searchNameField;
    private JTextField cityField;
    private JTextField zipField;
    private JRadioButton typeHouse;
    private JRadioButton typeLot;
    private ButtonGroup typeGroup;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JTextField minAreaField;
    private JTextField maxAreaField;
    private JTextField minRoomField;
    private JTextField maxRoomField;

    public NewSearchDialog() {
        super();

        // Position the dialog
        this.setLocationRelativeTo(FantasticBassoon.mainFrame);

        // Set the focus on the dialog
        this.setModal(true);

        // Set the title
        this.setTitle("Nouvelle Recherche");

        // Create a panel and a layout to put the components
        JPanel panel = new JPanel();
        this.add(panel);
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Define the layout
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create a field for naming the search
        JLabel searchNameLabel = new JLabel("Nom de la recherche :");
        searchNameField = new JTextField();

        // Creating fields for search criteria
        JLabel cityLabel = new JLabel("Ville :");
        cityField = new JTextField(10);

        JLabel zipLabel = new JLabel("Code Postal :");
        zipField = new JTextField(5);

        JLabel typeLabel = new JLabel("Type de bien :");
        typeHouse = new JRadioButton("Maison");
        typeLot = new JRadioButton("Terrain");
        typeGroup = new ButtonGroup();
        typeGroup.add(typeHouse);
        typeGroup.add(typeLot);

        JLabel priceLabel = new JLabel("Prix");
        JLabel minPriceLabel = new JLabel("min :");
        JLabel maxPriceLabel = new JLabel("max :");
        JLabel unitPriceLabel = new JLabel("€");
        minPriceField = new JTextField(6);
        maxPriceField = new JTextField(6);

        JLabel areaLabel = new JLabel("Surface");
        JLabel minAreaLabel = new JLabel("min :");
        JLabel maxAreaLabel = new JLabel("max :");
        JLabel unitAreaLabel = new JLabel("m²");
        minAreaField = new JTextField(4);
        maxAreaField = new JTextField(4);

        JLabel roomLabel = new JLabel("Nombre de pièces");
        JLabel minRoomLabel = new JLabel("min :");
        JLabel maxRoomLabel = new JLabel("max :");
        minRoomField = new JTextField(2);
        maxRoomField = new JTextField(2);

        // Creating buttons for submitting the form
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand("CANCEL");

        JButton createButton = new JButton("Créer la recherche");
        createButton.addActionListener(this);
        createButton.setActionCommand("CREATE");

        // Group layout Design
        // Horizontal layout
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchNameLabel)
                .addComponent(searchNameField))
            .addGroup(layout.createSequentialGroup()
                .addComponent(cityLabel)
                .addComponent(cityField)
                .addComponent(zipLabel)
                .addComponent(zipField))
            .addGroup(layout.createSequentialGroup()
                .addComponent(typeLabel)
                .addComponent(typeHouse)
                .addComponent(typeLot))
            .addGroup(layout.createSequentialGroup()
                .addComponent(priceLabel)
                .addComponent(minPriceLabel)
                .addComponent(minPriceField)
                .addComponent(unitPriceLabel)
                .addComponent(maxPriceLabel)
                .addComponent(maxPriceField)
                .addComponent(unitPriceLabel))
            .addGroup(layout.createSequentialGroup()
                .addComponent(areaLabel)
                .addComponent(minAreaLabel)
                .addComponent(minAreaField)
                .addComponent(unitAreaLabel)
                .addComponent(maxAreaLabel)
                .addComponent(maxAreaField)
                .addComponent(unitAreaLabel))
            .addGroup(layout.createSequentialGroup()
                .addComponent(roomLabel)
                .addComponent(minRoomLabel)
                .addComponent(minRoomField)
                .addComponent(maxRoomLabel)
                .addComponent(maxRoomField))
            .addGroup(layout.createSequentialGroup()
                .addComponent(cancelButton)
                .addComponent(createButton))
        );

        // Vertical layout
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(searchNameLabel)
                .addComponent(searchNameField))
            .addGroup(layout.createParallelGroup()
                .addComponent(cityLabel)
                .addComponent(cityField)
                .addComponent(zipLabel)
                .addComponent(zipField))
            .addGroup(layout.createParallelGroup()
                .addComponent(typeLabel)
                .addComponent(typeHouse)
                .addComponent(typeLot))
            .addGroup(layout.createParallelGroup()
                .addComponent(priceLabel)
                .addComponent(minPriceLabel)
                .addComponent(minPriceField)
                .addComponent(unitPriceLabel)
                .addComponent(maxPriceLabel)
                .addComponent(maxPriceField)
                .addComponent(unitPriceLabel))
            .addGroup(layout.createParallelGroup()
                .addComponent(areaLabel)
                .addComponent(minAreaLabel)
                .addComponent(minAreaField)
                .addComponent(unitAreaLabel)
                .addComponent(maxAreaLabel)
                .addComponent(maxAreaField)
                .addComponent(unitAreaLabel))
            .addGroup(layout.createParallelGroup()
                .addComponent(roomLabel)
                .addComponent(minRoomLabel)
                .addComponent(minRoomField)
                .addComponent(maxRoomLabel)
                .addComponent(maxRoomField))
            .addGroup(layout.createParallelGroup()
                .addComponent(cancelButton)
                .addComponent(createButton))
        );

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "CANCEL" :
                dispose();
                break;
            case "CREATE" :
                Query query = new Query();

                query.add(Query.Keys.Name, searchNameField.getText());
                query.add(Query.Keys.Location, cityField.getText());
                query.add(Query.Keys.Zip, zipField.getText());

                try {
                    query.add(Query.Keys.MinPrice,Integer.parseInt(minPriceField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Impossible de convertir le prix min (" + minPriceField.getText() + ")");
                }

                try {
                    query.add(Query.Keys.MaxPrice, Integer.parseInt(maxPriceField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Impossible de convertir le prix max (" + maxPriceField.getText() + ")");
                }

                try {
                    query.add(Query.Keys.MinArea, Integer.parseInt(minAreaField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Impossible de convertir la surface min (" + minAreaField.getText() + ")");
                }

                try {
                    query.add(Query.Keys.MaxArea, Integer.parseInt(maxAreaField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Impossible de convertir la surface max (" + maxAreaField.getText() + ")");
                }
                try {
                    query.add(Query.Keys.MinRoom, Integer.parseInt(minRoomField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Impossible de convertir le nombre de pièces min (" + minRoomField.getText() + ")");
                }
                try {
                    query.add(Query.Keys.MaxRoom, Integer.parseInt(maxRoomField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.print("Impossible de convertir le nombre de pièces max (" + maxRoomField.getText() + ")");
                }

                if (typeGroup.getSelection() == typeHouse.getModel()) {
                    query.add(Query.Keys.Type, AssetType.House);
                } else if (typeGroup.getSelection() == typeLot.getModel()) {
                    query.add(Query.Keys.Type, AssetType.Lot);
                }

                FantasticBassoon.searches.add(new Search(query));
                dispose();
                break;
        }
    }
}