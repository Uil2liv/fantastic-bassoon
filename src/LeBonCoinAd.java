import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LeBonCoinAd extends Ad {
    LeBonCoinAd(String url) {
        super(url);
    }

    @Override
    public void getField(WebDriver wd) {
        // Mandatory Fields
        this.fields.put(AdField.Title, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_title\"]/h1")).getText());
        this.fields.put(AdField.Price, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_price\"]/div/span"))
                .getAttribute("innerText").replaceAll("[^0-9]", ""));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");
        try {
            this.fields.put(AdField.Date,dateFormat.parse(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_date\"]")).getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch ( wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_real_estate_type\"]/div[2]")).getText() ) {
            case "Maison" :
                this.fields.put(AdField.Type, AssetType.House);
                break;
            case "Terrain" :
                this.fields.put(AdField.Type, AssetType.Lot);
                break;
        }

        this.fields.put(AdField.Description, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_description_container\"]/div[1]/span")).getText());
        // TODO Get the URL
        // TODO Get the Provider

        // Optional fields
        try {
            this.fields.put(AdField.NbRooms, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_rooms\"]/div[2]")).getText()));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de nombre de pièces.");
        }

        try {
            this.fields.put(AdField.Area, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_square\"]/div[2]")).getText().replaceAll("[^0-9]", "")));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de surface.");
        }
        try {
            this.fields.put(AdField.SubmitterId, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_custom_ref\"]/div[2]")).getText());
        } catch (NoSuchElementException e) {
            System.out.println("Pas de référence de l'émetteur.");
        }
        try {
            // TODO Don't get the value when the AD returns "Vierge"
            this.fields.put(AdField.GHG, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_ges\"]/div[2]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe émission GES.");
        }
        try {
            // TODO Don't get the value when the AD returns "Vierge"
            this.fields.put(AdField.Energy, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_energy_rate\"]/div[2]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe énergie.");
        }
        // TODO Get the Pictures
    }
}
