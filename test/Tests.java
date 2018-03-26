public class Tests {
    public static void main(String[] args){
        // Set path to Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        Provider p = new Provider(ProviderFactory.Providers.LeBonCoin);
        Query q = new Query();

        q.fields.put(Query.Keys.Location, "Josselin");
        q.fields.put(Query.Keys.Zip, "");
        q.fields.put(Query.Keys.MinArea, 123);
        //q.fields.put(Query.Keys.MaxArea, 234);
        q.fields.put(Query.Keys.Type, AssetType.House);

        p.search(q);
    }
}
