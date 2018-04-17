package app.core.immonotaires;

import app.core.common.Ad;
import app.core.common.ProviderFactory;
import app.core.common.ResultPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Vector;

public class ImmoNotairesResultPage extends ResultPage {
    public ImmoNotairesResultPage(WebDriver wd, ProviderFactory factory) {
        super(wd, factory);
    }

    @Override
    protected WebElement getNextPageWidget() {
        try {
            (new WebDriverWait(wd, 3))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[span[contains(text(), \"Les annonces immobilières autour de\")]]")));
            System.out.println("Marqueur trouvé");
            return null;
        } catch (TimeoutException e) {
            System.out.println("Marqueur non trouvé");
            try {
                return wd.findElement(By.className("next"));
            } catch (NoSuchElementException e1) {
                return null;
            }
        }
    }

    @Override
    protected Vector<Ad> getAds() {
        Vector<Ad> ads = new Vector<>();

        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        List<WebElement> elements;
        try {
             wd.findElement(By.xpath("//h1[span[contains(text(), \"Les annonces immobilières autour de\")]]"));
             elements = wd.findElements(By.xpath("//h1[span[contains(text(), \"Les annonces immobilières autour de\")]]/preceding::div[@class=\"resultat annonces\"]/div/h2/a"));
        } catch (NoSuchElementException e) {
            elements = (new WebDriverWait(wd, 3)).until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"resultat annonces\"]/div/h2/a")));
        }

        for (WebElement we : elements) {
            Ad ad = factory.createAd(we.getAttribute("href"));

            if (ad.getFields() != null)
                ads.add(ad);
        }

        this.nextPageWidget = getNextPageWidget();
        if (this.nextPageWidget != null) {
            goToNextPage();
            ads.addAll(getAds());
        }

        return ads;
    }
}
