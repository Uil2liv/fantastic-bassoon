import org.openqa.selenium.WebDriver;

public abstract class ProviderFactory {
    enum Providers {
        LeBonCoin
    }

    static public ProviderFactory createFactory(Providers p){
        switch (p) {
            case LeBonCoin:
                return new LeBonCoinFactory();
            default:
                return null;
        }
    }

    abstract public ResultPage createResultPage(WebDriver wd);
}
