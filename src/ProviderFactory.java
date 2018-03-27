import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class ProviderFactory {
    enum Providers {
        LeBonCoin
    }

    ProviderFactory() { }

    static public ProviderFactory createFactory(Providers p){
        switch (p) {
            case LeBonCoin:
                return new LeBonCoinFactory();
            default:
                return null;
        }
    }

    abstract public ResultPage createResultPage(WebDriver wd);

    abstract public FormPage createFormPage(WebDriver wd);

    abstract public Ad createAd(String url);
}
