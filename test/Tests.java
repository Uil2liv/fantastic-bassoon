import java.util.Vector;

public class Tests {
    public static void main(String[] args){
        // Set path to Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        Provider p = new Provider(ProviderFactory.Providers.LeBonCoin);
        Query q = new Query();

        q.fields.put(Query.Keys.Location, "Le roc saint andré");
        q.fields.put(Query.Keys.Zip, "56460");
        q.fields.put(Query.Keys.MinArea, 123);
        //q.fields.put(Query.Keys.MaxArea, 234);
        q.fields.put(Query.Keys.Type, AssetType.House);

        Vector<Asset> assets = p.search(q);
        System.out.println(assets);
    }
}
