import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Vector;

public class LeBonCoinResultPage extends ResultPage {
    public LeBonCoinResultPage(WebDriver wd, ProviderFactory factory){
        super(wd, factory);
    }

    @Override
    protected WebElement getNextPageWidget() {
        WebElement widget;

        try {
            widget = wd.findElement(By.id("next"));
            assert  (!widget.getAttribute("class").contains("disabled"));
        } catch (NoSuchElementException | AssertionError e) {
            return null;
        }

        return widget;
    }

    @Override
    protected Vector<Ad> getAds() {
        Vector<Ad> ads = new Vector<>();

        for (WebElement we : (new WebDriverWait(wd, 10).until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"listingAds\"]/section/section/ul/li/a"))))) {
            ads.add(factory.createAd(we.getAttribute("href")));
        }

        return ads;
    }
}
