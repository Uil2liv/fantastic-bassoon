import app.core.AssetType;
import app.core.common.Provider;
import app.core.common.ProviderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Tests {

    public static void main(String[] args){
        // Set path to Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        Provider p = new Provider(ProviderFactory.Providers.LeBonCoin);


        MyQuery q = new MyQuery();

        q.put(MyQuery.Keys.Location, "Le roc saint andré");
        q.put(MyQuery.Keys.Zip, "56460");
        q.put(MyQuery.Keys.MinArea, 123);
        q.put(MyQuery.Keys.MaxArea, 234);
        q.put(MyQuery.Keys.Type, AssetType.House);

        File f = new File("test.json");
        ObjectMapper o = new ObjectMapper();
        try {
            o.writerWithDefaultPrettyPrinter().writeValue(f, q);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
