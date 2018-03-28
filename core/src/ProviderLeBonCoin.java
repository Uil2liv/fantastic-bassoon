import app.core.common.Ad;
import app.core.Asset;
import app.core.common.Query;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
        class lbcSelect extends Select {
            public lbcSelect(WebElement we){
                super(we);
            }

            public void selectMaxValueLessThan(int max) {
                String maxValue = null;
                for (WebElement option : this.getOptions()) {
                    int value;
                    try {
                        value = Integer.parseInt(option.getText().replaceAll("[^0-9]", ""));
                        if (value <= max)
                            maxValue = option.getAttribute("value");
                        else
                            break;
                    } catch (NumberFormatException e){
                        System.out.println("Impossible d'évaluer la valeur \"" + option.getText() + "\" comme un entier");
                    }
                }
                if (maxValue != null)
                    this.selectByValue(maxValue);
            }

            public void selectMinValueGreaterThan(int min) {
                String minValue = null;
                for (WebElement option : this.getOptions()) {
                    int value;
                    try {
                        value = Integer.parseInt(option.getText().replaceAll("[^0-9]", ""));
                        if (value >= min) {
                            minValue = option.getAttribute("value");
                            break;
                        }

                    } catch (NumberFormatException e){
                        System.out.println("Impossible d'évaluer la valeur \"" + option.getText() + "\" comme un entier");
                    }
                }
                if (minValue != null)
                    this.selectByValue(minValue);            }
        }

        // Connect to the provider URL
        this.wd.get(this.url);

        // Get the element needed for launching query
        WebElement locWidget = wd.findElement(By.name("location_p"));
        WebElement houseWidget = wd.findElement(By.id("ret_1"));
        WebElement lotWidget = wd.findElement(By.id("ret_3"));
        WebElement submitWidget = wd.findElement(By.id("searchbutton"));
        lbcSelect minPriceWidget = new lbcSelect(wd.findElement(By.id("ps")));
        lbcSelect maxPriceWidget = new lbcSelect(wd.findElement(By.id("pe")));
        lbcSelect minAreaWidget =  new lbcSelect(wd.findElement(By.id("sqs")));
        lbcSelect maxAreaWidget =  new lbcSelect(wd.findElement(By.id("sqe")));
        lbcSelect minRoom =  new lbcSelect(wd.findElement(By.id("rooms_ros")));
        lbcSelect maxRoom = new lbcSelect(wd.findElement(By.id("rooms_roe")));

        // Enter the location criteria
        if (!q.getLocation().equals("")) {
            locWidget.sendKeys(q.getLocation() + " " + q.getZip());
            // Then confirm by clicking on the dropdown    list
            WebElement locConfirm = (new WebDriverWait(wd, 10))
                    .until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("location-list"), By.xpath("li[1]")));
            locConfirm.click();
        }

        // Enter min price
        if (q.getMinPrice() > 0) {
            minPriceWidget.selectMaxValueLessThan(q.getMinPrice());
        }

        // Enter max price
        if (q.getMaxPrice() > 0) {
            maxPriceWidget.selectMinValueGreaterThan(q.getMaxPrice());
        }

        // Enter min area
        if (q.getMinArea() > 0) {
            minAreaWidget.selectMaxValueLessThan(q.getMinArea());
        }

        // Enter max area
        if (q.getMaxArea() > 0) {
            maxAreaWidget.selectMinValueGreaterThan(q.getMaxArea());
        }

        // Enter min room
        if (q.getMinRoom() > 0) {
            minRoom.selectMaxValueLessThan(q.getMinRoom());
        }

        // Enter max room
        if (q.getMaxRoom() > 0) {
            maxRoom.selectMinValueGreaterThan(q.getMaxRoom());
        }

        // Move to app.core.Asset Types
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

        // Create an array to store all returned assets
        List<Asset> Assets = new ArrayList<>();
        Boolean isLastPage = false;
        // for each page
        while (!isLastPage) {
            // Create an app.core.Asset instance for each asset displayed on the page
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
            }

            // set the flag if the last page is reached
            try {
                WebElement nextPageWidget = wd.findElement(By.id("next"));
                if (nextPageWidget.getAttribute("class").contains("disabled")) {
                    isLastPage = true;
                } else {
                    this.ScrollTo(nextPageWidget.getLocation().y);
                    nextPageWidget.click();
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                isLastPage = true;
            }

        }

        wd.quit();
        return Assets;
    }
}
