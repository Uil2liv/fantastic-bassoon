import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class ProviderFactory {
    enum Providers {
        LeBonCoin
    }

    protected WebDriver wd;

    ProviderFactory() {
        wd = new ChromeDriver();
    }

    static public ProviderFactory createFactory(Providers p){
        switch (p) {
            case LeBonCoin:
                return new LeBonCoinFactory();
            default:
                return null;
        }
    }

    abstract public ResultPage createResultPage();

    abstract public FormPage createFormPage();
}
