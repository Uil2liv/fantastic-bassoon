package app.core.ouestfranceimmo;

import app.core.common.Ad;
import app.core.common.ProviderFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OuestFranceImmoAd extends Ad {
    public OuestFranceImmoAd(String url) {
        super(url);
        this.fields.put(AdField.Provider, ProviderFactory.Providers.OuestFranceImmo);
    }

    @Override
    public void getFields() {
        WebDriver wd = new ChromeDriver();

        wd.get(this.url.toString());

        this.fields.put(AdField.Title, wd.findElement(By.id("refMenuDescriptif")).getText().trim());
        this.fields.put(AdField.Description, wd.findElement(By.id("blockonDescriptif")).getText().trim());
        this.fields.put(AdField.Price, wd.findElement(By.xpath("//div[@id=\"blocCaractAnn\"]/ul/li[span[contains(text(), \"Prix\")]]/strong"))
                .getText().trim().replaceAll("[^0-9]", ""));
        this.fields.put(AdField.NbRooms, wd.findElement(By.xpath("//div[@id=\"blocCaractAnn\"]/ul/li[text()[contains(., \"Pièces\")]]/strong"))
                .getText().trim().replaceAll("[^0-9]", ""));
        Matcher m = Pattern.compile("/([^/]+/)*(?<Id>[0-9]+)\\.htm").matcher(this.url.getPath());
        m.matches();
        this.fields.put(AdField.ProviderId, m.group("Id"));

        try {
            String energy = wd.findElement(By.xpath("//span[@id=\"dpeCateg\"]/strong")).getText().trim();
            if (!energy.equals("N.C."))
                this.fields.put(AdField.Energy, energy);
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe énergie trouvée");
        }

        try {
            this.fields.put(AdField.Area, wd.findElement(By.xpath("//div[@id=\"blocCaractAnn\"]/ul/li[span[contains(text(), \"Surf. habitable\")]]/strong"))
                    .getText().trim().replaceAll("[^0-9]", ""));
        } catch (NoSuchElementException e) {
            try {
                this.fields.put(AdField.Area, wd.findElement(By.xpath("//div[@id=\"blocCaractAnn\"]/ul/li[span[contains(text(), \"Surf. terrain\")]]/strong"))
                        .getText().trim().replaceAll("[^0-9]", ""));
            } catch (NoSuchElementException e2) {
                System.out.println("Pas de surface trouvée");
            }
        }

        // Get pictures
        WebElement nextPic;
        try {
            nextPic = wd.findElement(By.xpath("//div[@id=\"slider\"]/ul[@class=\"flex-direction-nav\"]/li[@class=\"flex-nav-next\"]/a[@class=\"flex-next\"]"));
        } catch (NoSuchElementException e) {
            System.out.println("Une seule photo dans l'annonce.");
            nextPic = null;
        }
        Vector<String> pictures = new Vector<>();

        for (WebElement img : wd.findElements(By.xpath("//div[@id=\"slider\"]/div/ul/li[not(contains(@class, \"clone\"))]/img"))) {
            try {
                (new WebDriverWait(wd, 10)).until(ExpectedConditions.not(ExpectedConditions.attributeContains(img, "src", "nopingimg.png")));

                URL imgUrl = new URL(img.getAttribute("src"));
                m = Pattern.compile("^/([^/]+/)*(?<imgName>[a-zA-Z0-9_-]+\\.[a-zA-Z0-9]{3})$").matcher(imgUrl.getPath());
                m.matches();
                String imgName = m.group("imgName");

                pictures.add(imgName);
                if (nextPic != null)
                    nextPic.click();

            } catch (MalformedURLException e) {
                System.out.println("Cannot retrieve url: " + img.getAttribute("src"));
            }
        }

        if (pictures.size() > 0)
            this.fields.put(AdField.Pictures, pictures);

        wd.quit();
    }
}
