import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

abstract class Field {
    WebElement we;

    Field(WebElement we) {
        this.we = we;
    }

    abstract void fill(Object o);

    static protected void selectMaxValueLessThan(WebElement we, int val){
        Select s = new Select(we);
        String maxValue = null;
        for (WebElement option : s.getOptions()) {
            int value;
            try {
                value = Integer.parseInt(option.getText().replaceAll("[^0-9]", ""));
                if (value <= val)
                    maxValue = option.getAttribute("value");
                else
                    break;
            } catch (NumberFormatException e){
                System.out.println("Impossible d'évaluer la valeur \"" + option.getText() + "\" comme un entier");
            }
        }
        if (maxValue != null)
            s.selectByValue(maxValue);
    }

    static protected void selectMinValueGreaterThan(WebElement we, int val) {
        Select s = new Select(we);
        String minValue = null;
        for (WebElement option : s.getOptions()) {
            int value;
            try {
                value = Integer.parseInt(option.getText().replaceAll("[^0-9]", ""));
                if (value >= val) {
                    minValue = option.getAttribute("value");
                    break;
                }

            } catch (NumberFormatException e){
                System.out.println("Impossible d'évaluer la valeur \"" + option.getText() + "\" comme un entier");
            }
        }
        if (minValue != null)
            s.selectByValue(minValue);            }

}

class TextField extends Field {
    TextField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        if (!o.toString().equals(""))
            this.we.sendKeys((String)o);
    }
}

class SelectLTEField extends Field {
    SelectLTEField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        selectMaxValueLessThan(this.we, (int)o);
    }
}

class SelectGTEField extends Field {
    SelectGTEField(WebElement we) {
        super(we);
    }

    @Override
    public void fill(Object o) {
        selectMinValueGreaterThan(this.we, (int)o);
    }
}