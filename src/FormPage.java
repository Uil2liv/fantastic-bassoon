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
            if (q.fields.containsKey(fieldKey))
                fields.get(fieldKey).fill(q.fields.get(fieldKey));
        }
    }

    public ResultPage submit() {
        this.submitWidget.click();

        return factory.createResultPage();
    }
}