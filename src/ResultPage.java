import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
            goToNextPage();
            assets.addAll(getAssets());
        }

        return assets;
    }

    abstract protected Vector<Ad> getAds();

    private void goToNextPage() {
        nextPageWidget.click();
        this.nextPageWidget = getNextPageWidget();
    }
}
