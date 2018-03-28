import app.core.AssetType;
import app.core.Asset;
import app.core.common.Query;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class MyClass {
    public static void main(String[] args) {
        // Set path to Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", "core/chromedriver.exe");

        WebDriver wd = new ChromeDriver();
        ProviderLeBonCoin lbc = new ProviderLeBonCoin(wd);

        Query q = new Query("DummyQuery", AssetType.House, "Guer", "56380");
        q.setMinPrice(100000);
        q.setMaxPrice(300000);
        q.setMinRoom(3);
        q.setMaxRoom(6);
        q.setMinArea(88);
        q.setMaxArea(155);

        List<Asset> assets = lbc.Search(q);

        for (Asset a : assets) {
            System.out.println(a.getName() + ": " + a.getPrice() + "€ -> " + a.getUrl().toString());
            System.out.println(a.getDate());
            System.out.println(a.getArea() + "m² - " + a.getNbRooms() + " pièces");
            if (a.getGES() != null)
                System.out.println("Classe GES: " + a.getGES());
            if (a.getEnergyClass() != null)
                System.out.println("Classe Energie: " + a.getEnergyClass());
            System.out.println("Description:");
            System.out.println(a.getDescription());
            System.out.println();
        }

    }
}

