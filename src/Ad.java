import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.EnumMap;

public abstract class Ad extends Page{
    protected EnumMap<AdField, Object> fields = new EnumMap<>(AdField.class);

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
