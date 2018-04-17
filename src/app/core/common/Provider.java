package app.core.common;

import app.core.Asset;
import app.core.*;

import org.openqa.selenium.TimeoutException;
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
        Vector<Ad> ads = new Vector<>();

        formPage = factory.createFormPage(wd);
        formPage.access();
        try {
            formPage.fill(q);
            ResultPage resultPage = formPage.submit();
            ads = resultPage.getAds();
        } catch (TimeoutException e) {
            System.out.println("La page ne répond pas...");
        }
        wd.quit();
        return ads;
    }
}
