import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Vector;

public class Provider {
    protected FormPage formPage;
    protected ProviderFactory factory;

    public Provider(ProviderFactory.Providers provider){
        this.factory = ProviderFactory.createFactory(provider);
    }

    public Vector<Asset> search(Query q){
        WebDriver wd = new ChromeDriver();
        formPage = factory.createFormPage(wd);
        formPage.access();
        formPage.fill(q);
        ResultPage resultPage = formPage.submit();
        Vector<Asset> assets = resultPage.getAssets();
        wd.quit();
        return assets;
    }
}
