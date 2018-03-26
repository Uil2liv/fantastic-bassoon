import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeBonCoinAd extends Ad {
    LeBonCoinAd(String url) {
        super(url);
    }

    @Override
    public void getField(WebDriver wd) {
        this.fields.put(AdField.Title, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_title\"]/h1")).getText());
        this.fields.put(AdField.Price, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_price\"]/div/span")).getAttribute("innerText"));
    }
}
