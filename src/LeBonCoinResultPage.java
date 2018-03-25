import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Vector;

public class LeBonCoinResultPage extends ResultPage {
    public LeBonCoinResultPage(WebDriver wd, ProviderFactory factory){
        super(wd, factory);
    }

    @Override
    protected WebElement getNextPageWidget() {
        WebElement widget = wd.findElement(By.id("next"));
        if (widget.getAttribute("class").contains("disabled"))
            return null;
        else
            return widget;
    }

    @Override
    protected Vector<Ad> getAds() {
        // TODO Implement getAds();
        return null;
    }
}
