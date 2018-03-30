package app.core.common.fields;

import org.openqa.selenium.WebElement;

public class SelectGTEField extends Field {
    public SelectGTEField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        selectMinValueGreaterThan(this.we, (int)o);
    }
}
