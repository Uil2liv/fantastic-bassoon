import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProviderLeBonCoin implements IProvider {
    private WebDriver wd;
    private String url;
    private int scrollOffset;

    // Constructor
    public ProviderLeBonCoin(WebDriver wd){
        this.wd = wd;
        this.url = "https://www.leboncoin.fr/ventes_immobilieres/offres/";
        this.scrollOffset = wd.manage().window().getSize().height / 2;
    }

    // Methods
    private int Scroll(int offset){
        JavascriptExecutor je = (JavascriptExecutor)wd;
        je.executeScript("window.scrollBy(0," + offset + ");", "");

        this.scrollOffset += offset;

        // Check boundaries
        scrollOffset = Math.max(wd.manage().window().getSize().height / 2, scrollOffset);

        return this.scrollOffset;
    }

    private int ScrollTo(int offset){
        return Scroll(offset - scrollOffset);
    }

    private int ScrollToTop(){
        return ScrollTo(0);
    }

    @Override
    public List<Asset> Search(Query q) {
        // Connect to the provider URL
        this.wd.get(this.url);

        // Get the element needed for launching query
        WebElement locWidget = wd.findElement(By.name("location_p"));
        WebElement houseWidget = wd.findElement(By.id("ret_1"));
        WebElement lotWidget = wd.findElement(By.id("ret_3"));
        WebElement submitWidget = wd.findElement(By.id("searchbutton"));

        // Enter the location criteria
        locWidget.sendKeys(q.getLocation() + " " + q.getZipCode());
        // Then confirm by clicking on the dropdown list
        WebElement locConfirm = (new WebDriverWait(wd, 10))
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("location-list"),By.xpath("li[1]")));
        locConfirm.click();

        // Move to Asset Types
        int offset = houseWidget.getLocation().getY();
        this.ScrollTo(offset);

        // Click on the correct asset type
        switch ( q.getType() ) {
            case House:
                if ( ! houseWidget.isSelected() ){
                    houseWidget.click();
                }
                if ( lotWidget.isSelected() ) {
                    lotWidget.click();
                }
                break;

            case Lot:
                if ( houseWidget.isSelected() ){
                    houseWidget.click();
                }
                if ( ! lotWidget.isSelected() ) {
                    lotWidget.click();
                }
                break;
        }

        // Move to Submit button
        ScrollToTop();

        // Click to submit the query
        submitWidget.click();

        // Create an Asset instance for each asset displayed on the page
        List<Asset> Assets = new ArrayList<Asset>();
        List<WebElement> assetElements = (new WebDriverWait(wd, 10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"listingAds\"]/section/section/ul/li")));
        List<Ad> ads = new ArrayList<Ad>();

        for (WebElement el : assetElements) {
            URL adUrl = null;
            try {
                adUrl = new URL(el.findElement(By.tagName("a")).getAttribute("href"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Ad ad = new AdLeBonCoin(adUrl);

            Asset a = ad.getAsset();

            Assets.add(a);
/*            String name = el.findElement(By.tagName("a")).getAttribute("title");

            int price = Integer.parseInt(el.findElement(By.xpath(".//h3[@itemprop=\"price\"]")).getAttribute("content"));

            URL assetUrl = null;
            try {
                assetUrl = new URL(el.findElement(By.tagName("a")).getAttribute("href"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Assets.add(new Asset(name, price, assetUrl));*/
        }


        return Assets;
    }
}
