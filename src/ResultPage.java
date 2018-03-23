import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Vector;

abstract class ResultPage extends Page {
    protected final ProviderFactory factory;
    protected WebElement nextPageWidget;

    ResultPage(WebDriver wd, ProviderFactory factory) {
        super(wd);
        this.factory = factory;
        this.nextPageWidget = getNextPageWidget();
    }

    protected abstract WebElement getNextPageWidget();

    public Vector<Asset> getAssets() {
        Vector<Asset> assets = new Vector<>();
        Vector<Ad> ads = getAds();
        ads.forEach(ad -> assets.add(ad.getAsset()));

        if (nextPageWidget != null) {
            ResultPage nextPage = goToNextPage();
            assets.addAll(nextPage.getAssets());
        }

        return assets;
    }

    abstract protected Vector<Ad> getAds();

    private ResultPage goToNextPage() {
        nextPageWidget.click();
        return factory.createResultPage(wd);
    }
}
