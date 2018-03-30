package app.core.common.fields;

import org.openqa.selenium.WebElement;

public class TextField extends Field {
    public TextField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        if (!o.toString().equals(""))
            this.we.sendKeys((String)o);
    }
}
