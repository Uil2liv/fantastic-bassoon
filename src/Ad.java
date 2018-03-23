import org.openqa.selenium.WebDriver;

public abstract class Ad extends Page{
    public Ad(String url, WebDriver wd) {
        super(url, wd);
    }

    public Asset getAsset() {
        Asset asset = new Asset();
        // TODO: Implement EnumMap for storing values
        //       the getAsset method should use that map to create the asset
        //       An abstract method to retrieve the fields must be added
        return asset;
    }
}
