package app.core.ouestfranceimmo;

import app.core.common.Ad;
import app.core.common.ProviderFactory;
import app.core.common.ResultPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Vector;

public class OuestFranceImmoResultPage extends ResultPage {
    public OuestFranceImmoResultPage(WebDriver wd, ProviderFactory pf) { super(wd, pf); }

    @Override
    protected WebElement getNextPageWidget() {
        return null;
    }

    @Override
    protected Vector<Ad> getAds() {
        return null;
    }
}
