package app.core.common;

import app.core.*;

import app.core.common.fields.Field;
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

    @Override
    public void access(){
        wd.get(url.toString());
        fields = getFields();
        submitWidget = getSubmitWidget();
    }

    protected abstract EnumMap<Query.Keys, Field> getFields();

    protected abstract WebElement getSubmitWidget();

    public void fill(Query q){
        for (Query.Keys fieldKey : fields.keySet()){
            if (q.containsKey(fieldKey))
                ScrollTo(fields.get(fieldKey).we.getLocation().getY());
                fields.get(fieldKey).fill(q.get(fieldKey));
        }
    }

    public ResultPage submit() {
        ScrollTo(this.submitWidget.getLocation().getY());
        this.submitWidget.click();

        return factory.createResultPage(wd);
    }
}
