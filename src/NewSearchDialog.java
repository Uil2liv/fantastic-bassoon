import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewSearchDialog extends JDialog implements ActionListener{
    JTextField searchNameField;
    JTextField cityField;
    JTextField zipField;

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
        cityField = new JTextField();

        JLabel zipLabel = new JLabel("Code Postal :");
        zipField = new JTextField();

        // Creating buttons for submitting the form
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand("CANCEL");

        JButton createButton = new JButton("Créer la recherche");
        createButton.addActionListener(this);
        createButton.setActionCommand("CREATE");

        // Group layout Design
        // Horizontal layout
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(searchNameLabel)
                .addComponent(cityLabel))
            .addGroup(layout.createParallelGroup()
                .addComponent(searchNameField)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(cityField)
                    .addComponent(zipLabel)
                    .addComponent(zipField))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(cancelButton)
                    .addComponent(createButton)))
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
                Query query = new Query(searchNameField.getText(), AssetType.House, cityField.getText(), zipField.getText());
                FantasticBassoon.searches.add(query);
                dispose();
                break;
        }
    }
}