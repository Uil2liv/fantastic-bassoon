import app.FantasticBassoon;
import app.core.AssetType;
import app.core.Query;
import app.core.common.FormPage;
import app.core.common.Provider;
import app.core.common.ProviderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {

    public static void main(String[] args){
        URL webDriverPath = Tests.class.getResource("/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", webDriverPath.getPath());

        WebDriver wd = new ChromeDriver();

        Query q = new Query();
        q.add(Query.Keys.Location, "Guer");
        q.add(Query.Keys.Zip, "56380");
        q.add(Query.Keys.Type, AssetType.House);
        q.add(Query.Keys.MaxPrice, 200000);
        q.add(Query.Keys.MinPrice, 100000);
        q.add(Query.Keys.MinArea, 100);
        q.add(Query.Keys.MaxArea, 200);
        q.add(Query.Keys.MinRoom, 3);

        ProviderFactory p = ProviderFactory.createFactory(ProviderFactory.Providers.OuestFranceImmo);
        FormPage form = p.createFormPage(wd);
        form.access();
        form.fill(q);
        form.submit();
    }
}
