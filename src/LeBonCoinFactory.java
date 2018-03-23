import org.openqa.selenium.WebDriver;

public class LeBonCoinFactory extends ProviderFactory {
    @Override
    public ResultPage createResultPage(WebDriver wd) {
        return new LeBonCoinResultPage(wd, this);
    }
}
