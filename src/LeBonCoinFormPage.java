import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.EnumMap;

public class LeBonCoinFormPage extends FormPage {
    private static final String url = "https://www.leboncoin.fr/ventes_immobilieres/offres/";

    public LeBonCoinFormPage(WebDriver wd, ProviderFactory pf) {
        super(url, wd, pf);
    }

    @Override
    protected EnumMap<Query.Keys, Field> getFields() {
        EnumMap<Query.Keys, Field> fields = new EnumMap<>(Query.Keys.class);

        fields.put(Query.Keys.Location, new TextField(wd.findElement(By.name("location_p"))));
        fields.put(Query.Keys.MinArea, new SelectLTEField(wd.findElement(By.id("sqs"))));
        fields.put(Query.Keys.MaxArea, new SelectGTEField(wd.findElement(By.id("sqe"))));
        fields.put(Query.Keys.MinPrice, new SelectLTEField(wd.findElement(By.id("ps"))));
        fields.put(Query.Keys.MaxPrice, new SelectGTEField(wd.findElement(By.id("pe"))));
        fields.put(Query.Keys.MinRoom, new SelectLTEField(wd.findElement(By.id("ros"))));
        fields.put(Query.Keys.MaxRoom, new SelectGTEField(wd.findElement(By.id("roe"))));

        return fields;
    }

    @Override
    public void fill(Query q) {
        for (Query.Keys fieldKey : fields.keySet()){
            if (q.fields.containsKey(fieldKey)) {
                switch (fieldKey) {
                    case Location:
                        // Concatenate City and Zip Code in the location field
                        fields.get(fieldKey).fill(q.fields.get(fieldKey) + " " + q.fields.get(Query.Keys.Zip));

                        // Confirm selection by clicking on the first element of the dropdown list.
                        WebElement locConfirm = (new WebDriverWait(wd, 10))
                                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("location-list"), By.xpath("li[1]")));
                        locConfirm.click();

                        break;
                    default:
                        fields.get(fieldKey).fill(q.fields.get(fieldKey));
                }
            }
        }
    }

    @Override
    protected WebElement getSubmitWidget() {
        return wd.findElement(By.id("searchbutton"));
    }
}
