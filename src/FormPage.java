import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.EnumMap;

public class FormPage extends Page {
    private EnumMap<Query.Keys, Field> fields = new EnumMap(Query.Keys.class);
    private WebElement submitWidget;

    public FormPage(String url, WebDriver wd) {
        super(url, wd);
    }

    public void access(WebDriver wd){
        wd.get(url.toString());
    }

    public void fill(Query q){
        fields.keySet().forEach( key -> fields.get(key).fill(q.fields.get(key)));
    }

    public ResultPage submit() {
        this.submitWidget.click();
        return new ResultPage(wd);
    }
}
