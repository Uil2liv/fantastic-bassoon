import app.core.Asset;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;

abstract class Ad_old {
    // Properties
    protected WebDriver wd;
    protected URL url;

    // Contructors
    public Ad(URL url) {
        this.wd = new ChromeDriver();
        this.url = url;
    }

    // Methods
    abstract Asset getAsset();
}
