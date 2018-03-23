import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.EnumMap;

public abstract class FormPage extends Page {
    final protected ProviderFactory factory;
    protected EnumMap<Query.Keys, Field> fields;
    protected WebElement submitWidget;

    public FormPage(String url, WebDriver wd, ProviderFactory pf) {
        super(url, wd);
        factory = pf;
    }

    public void access(WebDriver wd){
        wd.get(url.toString());
        fields = getFields();
        submitWidget = getSubmitWidget();
    }

    protected abstract EnumMap<Query.Keys, Field> getFields();

    protected abstract WebElement getSubmitWidget();

    public void fill(Query q){
        fields.keySet().forEach( key -> fields.get(key).fill(q.fields.get(key)));
    }

    public ResultPage submit() {
        this.submitWidget.click();

        return factory.createResultPage(wd);
    }
}
