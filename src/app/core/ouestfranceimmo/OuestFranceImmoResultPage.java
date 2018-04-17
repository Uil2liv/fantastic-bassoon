package app.core.ouestfranceimmo;

import app.core.common.Ad;
import app.core.common.ProviderFactory;
import app.core.common.ResultPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.Vector;

public class OuestFranceImmoResultPage extends ResultPage {
    public OuestFranceImmoResultPage(WebDriver wd, ProviderFactory pf) { super(wd, pf); }

    @Override
    protected WebElement getNextPageWidget() {
        try {
            return wd.findElement(By.xpath("//a[@title=\"Page suivante\"]"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return null;
        }
    }

    @Override
    protected Vector<Ad> getAds() {
        Vector<Ad> ads = new Vector<>();

        for (WebElement we : (new WebDriverWait(wd, 10).until(ExpectedConditions
        .presenceOfAllElementsLocatedBy(By.xpath("//a[contains(@class, \"annLink\")]"))))) {

            Ad ad = factory.createAd(we.getAttribute("href"));
            if (ad.getFields() != null)
                ads.add(ad);
        }

        return ads;
    }
}
