import org.openqa.selenium.By;

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
        String price = wd.findElement(By.xpath("//*[@data-qa-id=\"adview_price\"]/div/span")).getAttribute("innerText");
        System.out.println("Element text: " + price);
        System.out.println("Element cleant: " + price.replaceAll("[^0-9]", ""));
        System.out.println("Element int: " + Integer.parseInt(price.replaceAll("[^0-9]", "")));

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

        wd.quit();

        return a;
    }
}