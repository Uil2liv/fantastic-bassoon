package app.core.common.fields;

import org.openqa.selenium.WebElement;

public class SelectLTEField extends Field {
    public SelectLTEField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        selectMaxValueLessThan(this.we, (int)o);
    }
}
