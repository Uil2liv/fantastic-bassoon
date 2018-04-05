package app.core.leboncoin;

import app.core.AssetType;
import app.core.common.Ad;
import app.core.common.Provider;
import app.core.common.ProviderFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeBonCoinAd extends Ad {
    LeBonCoinAd(String url) {
        super(url);
        this.fields.put(AdField.Provider, ProviderFactory.Providers.LeBonCoin);
    }

    @Override
    public void getFields() {
        WebDriver wd = new ChromeDriver();

        wd.get(this.url.toString());

        // Mandatory Fields
        this.fields.put(AdField.Title, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_title\"]/h1")).getText());
        this.fields.put(AdField.Price, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_price\"]/div/span"))
                .getAttribute("innerText").replaceAll("[^0-9]", "")));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");
        try {
            this.fields.put(AdField.Date,dateFormat.parse(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_date\"]")).getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Matcher m = Pattern.compile("/[^/]+/(?<Id>[0-9]+)\\.htm").matcher(this.url.getPath());
        m.matches();
        this.fields.put(AdField.ProviderId, m.group("Id"));

        switch ( wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_real_estate_type\"]/div/div[2]")).getText() ) {
            case "Maison" :
                this.fields.put(AdField.Type, AssetType.House);
                break;
            case "Terrain" :
                this.fields.put(AdField.Type, AssetType.Lot);
                break;
        }

        this.fields.put(AdField.Description, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_description_container\"]/div[1]/span")).getText());
        // TODO Get the URL
        // TODO Get the app.core.common.Provider

        // Optional fields
        try {
            this.fields.put(AdField.NbRooms, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_rooms\"]/div/div[2]")).getText()));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de nombre de pièces.");
        }

        try {
            this.fields.put(AdField.Area, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_square\"]/div/div[2]")).getText().replaceAll("[^0-9]", "")));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de surface.");
        }
        try {
            this.fields.put(AdField.SubmitterId, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_custom_ref\"]/div/div[2]")).getText());
        } catch (NoSuchElementException e) {
            System.out.println("Pas de référence de l'émetteur.");
        }
        try {
            this.fields.put(AdField.GHG, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_ges\"]/div/div[2]/div/div[contains(@class, \"_1sd0z\")]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe émission GES.");
        }
        try {
            this.fields.put(AdField.Energy, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_energy_rate\"]/div/div[2]/div/div[contains(@class, \"_1sd0z\")]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe énergie.");
        }
        // TODO Get the Pictures

        wd.quit();
    }
}
