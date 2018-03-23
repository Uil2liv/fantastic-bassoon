import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class Provider {
    protected FormPage formPage;
    protected String searchFormUrl;
    protected ProviderFactory factory;

    public Provider(ProviderFactory.Providers provider, String searchFormUrl){
        this.factory = ProviderFactory.createFactory(provider);
        this.searchFormUrl = searchFormUrl;
    }

    public AssetsList search(Query q){
        WebDriver wd = new ChromeDriver();
        formPage = factory.createFormPage(searchFormUrl, wd);
        formPage.access();
        formPage.fill(q);
        ResultPage resultPage = formPage.submit();
        AssetsList assets = resultPage.getAssets();
        return assets;
    }
}
