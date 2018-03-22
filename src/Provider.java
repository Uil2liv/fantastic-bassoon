import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Vector;

public abstract class Provider {
    enum Type {
        leBonCoin
    }

    protected FormPage formPage;
    protected String searchFormUrl;

    public AssetsList search(Query q){
        WebDriver wd = new ChromeDriver();
        formPage = new FormPage(searchFormUrl, wd);
        formPage.access();
        formPage.fill(q);
        ResultPage resultPage = formPage.submit();
        AssetsList assets = resultPage.getAssets();
        return assets;
    }

    public static Provider getProvider(Provider.Type provider) {
        switch (provider) {
            case leBonCoin:
                return new LeBonCoinProvider();
        }
    }
}
