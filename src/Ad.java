import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.EnumMap;

public abstract class Ad extends Page{
    protected EnumMap<AdField, String> fields = new EnumMap<>(AdField.class);

    public Ad(String url) {
        super(url);
    }

    public Asset getAsset() {
        Asset asset = new Asset();

        wd = new ChromeDriver();
        wd.get(url.toString());
        getField(wd);

        for (AdField fieldKey : this.fields.keySet()) {
            asset.add(fieldKey, this.fields.get(fieldKey));
        }
        wd.quit();

        // TODO: Implement EnumMap for storing values
        //       the getAsset method should use that map to create the asset
        //       An abstract method to retrieve the fields must be added
        return asset;
    }

    public abstract void getField(WebDriver wd);

    enum AdField {
        Id,
        Title,
        Price,
        Date,
        Type,
        NbRooms,
        Area,
        ProviderId,
        SubmitterId,
        Description,
        GHG,
        Energy,
        Submitter,
    }
}
