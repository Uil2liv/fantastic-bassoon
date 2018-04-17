package app.core.immonotaires;

import app.core.Asset;
import app.core.AssetType;
import app.core.Query;
import app.core.common.Ad;
import app.core.common.FormPage;
import app.core.common.ProviderFactory;
import app.core.common.fields.CheckBoxField;
import app.core.common.fields.Field;
import app.core.common.fields.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Timer;

public class ImmoNotairesFormPage extends FormPage {
    private static final String url = "https://www.immobilier.notaires.fr/";

    public ImmoNotairesFormPage(WebDriver wd, ProviderFactory pf) {
        super(url, wd, pf);
    }

    @Override
    public void access() {
        super.access();
        WebElement immoCheck = wd.findElement(By.xpath("//label[input[@id=\"immoCheck\"]]/span[@class=\"custom-check\"]"));
        WebElement encheresCheck = wd.findElement(By.xpath("//label[input[@id=\"encheresCheck\"]]/span[@class=\"custom-check\"]"));

        (new WebDriverWait(wd, 10)).until(ExpectedConditions.elementToBeClickable(immoCheck)).click();
        (new WebDriverWait(wd, 10)).until(ExpectedConditions.elementToBeClickable(encheresCheck)).click();
    }

    @Override
    protected EnumMap<Query.Keys, Field> getFields() {
        EnumMap<Query.Keys, Field> fields = new EnumMap<>(Query.Keys.class);

        fields.put(Query.Keys.Location, new TextField((new WebDriverWait(wd, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class=\"select2-search-field\"]/input")))));
        fields.put(Query.Keys.MinPrice, new TextField(wd.findElement(By.xpath("//input[@placeholder=\"Prix min.\"]"))));
        fields.put(Query.Keys.MaxPrice, new TextField(wd.findElement(By.xpath("//input[@placeholder=\"Prix max.\"]"))));
        fields.put(Query.Keys.MinArea, new TextField(wd.findElement(By.xpath("//input[@placeholder=\"Surface min.\"]"))));

        EnumMap<AssetType, WebElement> boxes = new EnumMap<>(AssetType.class);
        boxes.put(AssetType.House, wd.findElement(By.xpath("//label[input[@id=\"dropdownTypeBiensMaison / villa\"]]")));
        boxes.put(AssetType.Lot, wd.findElement(By.xpath("//label[input[@id=\"dropdownTypeBiensTerrain\"]]")));
        fields.put(Query.Keys.Type, new CheckBoxField(wd.findElement(By.xpath("//div[@class=\"types-biens dropdown\"]")), boxes));

        return fields;
    }

    @Override
    public void fill(Query q) {
        for (Query.Keys fieldKey : fields.keySet()){
            if (q.containsKey(fieldKey)) {
                ScrollTo(fields.get(fieldKey).we.getLocation().getY());
                switch (fieldKey) {
                    case Location:
                        fields.get(fieldKey).we.click();
                        fields.get(fieldKey).fill(q.get(fieldKey));
                        WebElement locChoice = (new WebDriverWait(wd, 10))
                                .until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-select-choices-row-0-0")));
                        locChoice.click();
                        break;
                    case Type:
                        // Click on the fake Select
                        WebElement select = fields.get(fieldKey).we;
                        select.click();
                        WebElement selectAll = wd.findElement(By.xpath("//label[input[@id=\"dropdownTypeBiensTous types de biens\"]]"));
                        selectAll.click();
                        fields.get(fieldKey).fill(q.get(fieldKey));
                        select.click();
                        break;
                    default:
                        fields.get(fieldKey).fill(q.get(fieldKey));
                }
                // Let's slow down the form filling to prevent field reset while we're filling them
                try { Thread.sleep(600); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    protected WebElement getSubmitWidget() {
        return wd.findElement(By.xpath("//button[contains(text(),\"Rechercher\")]"));
    }
}
