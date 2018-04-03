package app.core.common;

import app.core.Asset;
import app.core.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Vector;

public class Provider {
    protected FormPage formPage;
    protected ProviderFactory factory;

    public Provider(ProviderFactory.Providers provider){
        this.factory = ProviderFactory.createFactory(provider);
    }

    public Vector<Ad> search(Query q){
        WebDriver wd = new ChromeDriver();
        formPage = factory.createFormPage(wd);
        formPage.access();
        formPage.fill(q);
        ResultPage resultPage = formPage.submit();
        Vector<Ad> ads = resultPage.getAds();
        wd.quit();
        return ads;
    }
}
