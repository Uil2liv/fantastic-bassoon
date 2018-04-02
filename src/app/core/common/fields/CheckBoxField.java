package app.core.common.fields;

import app.core.AssetType;
import org.openqa.selenium.WebElement;

import java.util.EnumMap;

public class CheckBoxField extends Field {
    private EnumMap<AssetType, WebElement> boxes;

    public CheckBoxField(WebElement we, EnumMap<AssetType, WebElement> boxes) {
        super(we);
        this.boxes = boxes;
    }

    @Override
    public void fill(Object o) {
        for (AssetType boxKey : boxes.keySet()){
            if (boxes.get(boxKey).isSelected() != (boxKey.toString().equals(o.toString())))
                boxes.get(boxKey).click();
        }
    }
}
