package app.core.ouestfranceimmo;

import app.core.AssetType;
import app.core.Query;
import app.core.common.FormPage;
import app.core.common.ProviderFactory;
import app.core.common.fields.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.EnumMap;

public class OuestFranceImmoFormPage extends FormPage {
    private static final String url = "https://www.ouestfrance-immo.com/";

    public OuestFranceImmoFormPage (WebDriver wd, ProviderFactory pf) {
        super(url, wd, pf);
    }

    @Override
    protected EnumMap<Query.Keys, Field> getFields() {
        // Expand criteria
        (new WebDriverWait(wd, 10)).until(ExpectedConditions.elementToBeClickable(By.id("jsMoreField"))).click();
//        wd.findElement(By.id("jsMoreField")).click();

        EnumMap<Query.Keys, Field> fields = new EnumMap<>(Query.Keys.class);
        fields.put(Query.Keys.Location, new TextField(wd.findElement(By.id("ville"))));
        fields.put(Query.Keys.MaxPrice, new TextField(wd.findElement(By.id("prixMax"))));
        fields.put(Query.Keys.MinPrice, new TextField(wd.findElement(By.id("prixMin"))));
        fields.put(Query.Keys.MinArea, new TextField(wd.findElement(By.id("surfaceMin"))));
        fields.put(Query.Keys.MaxArea, new TextField(wd.findElement(By.id("surfaceMax"))));
        fields.put(Query.Keys.MinRoom, new DefaultField(wd.findElement(By.xpath("//div[@class=\"contPieces\"]/div"))));

        EnumMap<AssetType, WebElement> boxes = new EnumMap<>(AssetType.class);
        boxes.put(AssetType.House, wd.findElement(By.id("typeBien_V_maison")));
        boxes.put(AssetType.Lot, wd.findElement(By.id("typeBien_V_terrain")));
        fields.put(Query.Keys.Type, new CheckBoxField(wd.findElement(By.id("multiSelectV")), boxes));

        return fields;
    }

    @Override
    public void fill(Query q) {
        for (Query.Keys fieldKey : fields.keySet()){
            if (q.containsKey(fieldKey)) {
                ScrollTo(fields.get(fieldKey).we.getLocation().getY());
                switch (fieldKey) {
                    case Location:
                        // Concatenate City and Zip Code in the location field
                        fields.get(fieldKey).fill(q.get(fieldKey));
                        WebElement locationList = (new WebDriverWait(wd, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("ville_autocomplete")));
                        WebElement locationItem = (new WebDriverWait(wd, 10)).until(ExpectedConditions.presenceOfNestedElementLocatedBy(locationList, By.xpath("a[text()[contains(.,\"" + q.get(fieldKey) + " \")]]")));
                        while(!locationItem.isDisplayed())
                            new Actions(wd).sendKeys(Keys.ARROW_DOWN);
                        locationItem.click();
                        break;
                    case Type:
                        // Expand Asset Types Menu
                        fields.get(fieldKey).we.click();
                        fields.get(fieldKey).fill(q.get(fieldKey));
                        fields.get(fieldKey).we.click();
                        break;
                    case MinRoom:
                        WebElement optionList = fields.get(fieldKey).we;
                        ScrollTo(optionList.getLocation().getY());
                        optionList.click();
                        try {
                            WebElement option = (new WebDriverWait(wd, 10)).until(ExpectedConditions.elementToBeClickable(By.id("pieces_" + q.get(fieldKey))));
                            option.click();
                        } catch (TimeoutException e) {
                            System.out.println("Impossible de choisir le nombre de pièces minimum.");
                        }
                        break;
                    default:
                        ScrollTo(fields.get(fieldKey).we.getLocation().getY());
                        fields.get(fieldKey).fill(q.get(fieldKey));
                }
            }
        }
    }
    @Override
    protected WebElement getSubmitWidget() {
        return wd.findElement(By.id("btnTrouvez"));
    }
}
