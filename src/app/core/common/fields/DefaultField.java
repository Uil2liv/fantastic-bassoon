package app.core.common.fields;

import org.openqa.selenium.WebElement;

public class DefaultField extends Field {
    public DefaultField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        we.sendKeys(o.toString());
    }
}
