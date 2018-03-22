import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Vector;

abstract class ResultPage extends Page {
    protected WebElement nextPageWidget;

    public ResultPage(WebDriver wd) {
        super(wd);
        getNextPageWidget();
    }

    protected abstract WebElement getNextPageWidget();

    public AssetsList getAssets() {
        Vector<Asset> assets = new Vector<>();
        Vector<Ad> ads = getAds();
        ads.forEach(ad -> assets.add(ad.getAsset()));

        if (nextPageWidget == null) {
            return assets;
        } else {
            nextPageWidget.click();
            return assets.addAll();
        }
    }

    private Vector<Ad> getAds() {
        // TODO implement get ads
    }

    private ResultPage goToNextPage() {
        nextPageWidget.click();
        return new ResultPage(wd);
    }
}
