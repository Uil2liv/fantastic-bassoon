import app.core.AssetType;
import app.core.common.Ad;
import app.core.Asset;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdLeBonCoin extends Ad {
    // Constructors
    public AdLeBonCoin(URL url) {
        super(url);
    }

    public Asset getAsset() {
        Asset a = new Asset();

        // Access to the ad page
        wd.get(url.toString());

        // Retrieve information needed
        a.setUrl(url);

        a.setName(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_title\"]/h1")).getText());
        a.setPrice(Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_price\"]/div/span"))
                .getAttribute("innerText").replaceAll("[^0-9]", "")));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");
        try {
            a.setDate(format.parse(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_date\"]")).getText()));
        } catch (ParseException pe){
            pe.printStackTrace();
        }

        a.setDescription(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_description_container\"]/div[1]/span")).getText());
        switch ( wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_real_estate_type\"]/div[2]")).getText() ) {
            case "Maison" :
                a.setType(AssetType.House);
                break;
            case "Terrain" :
                a.setType(AssetType.Lot);
                break;
        }

        a.setNbRooms(Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_rooms\"]/div[2]")).getText()));

        a.setArea(Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_square\"]/div[2]")).getText().replaceAll("[^0-9]", "")));

        try {
            a.setGES(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_ges\"]/div[2]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de GES trouvée.");
        }

        try {
            a.setEnergyClass(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_energy_rate\"]/div[2]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe énergétique trouvée.");
        }

        // Click on the slideshow to display all the pictures
        wd.findElement(By.xpath("//*[@data-qa-id=\"slideshow_container\"]/div[1]/div[1]/div[1]/div[1]/div[2]/img")).click();
        for (WebElement webElement : wd.findElements(By.xpath("//*[@data-qa-id=\"lightbox_item\"]/img"))) {
            a.addPicture(webElement.getAttribute("src"));
        }

        wd.quit();

        return a;
    }
}