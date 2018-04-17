package app.core.common;

import app.core.Asset;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Vector;

public abstract class ResultPage extends Page {
    protected final ProviderFactory factory;
    protected WebElement nextPageWidget;

    public ResultPage(WebDriver wd, ProviderFactory factory) {
        super(wd);
        this.factory = factory;
        this.nextPageWidget = getNextPageWidget();
    }

    protected abstract WebElement getNextPageWidget();

    abstract protected Vector<Ad> getAds();

    protected void goToNextPage() {
        nextPageWidget.click();
        this.nextPageWidget = getNextPageWidget();
    }
}
