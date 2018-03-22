import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

abstract class Page {
    protected URL url;
    protected WebDriver wd;

    public Page(WebDriver wd) {
        this.wd = wd;
    }

    public Page(String url, WebDriver wd) {
        this.wd = wd;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("Impossible d'accéder à l'adresse : " + url);
            e.printStackTrace();
        }
    }

    public void access() {
        wd.get(url.toString());
    }
}
