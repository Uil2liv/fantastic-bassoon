package app.core.immonotaires;

import app.core.common.Ad;
import app.core.common.ProviderFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImmoNotairesAd extends Ad {
    public ImmoNotairesAd(String url) {
        super(url);
        this.fields.put(AdField.Provider, ProviderFactory.Providers.ImmoNotaires);
    }

    @Override
    public Ad getFields() {
        wd = new ChromeDriver();
        wd.get(this.url.toString());

        Pattern p = Pattern.compile(".*idAnnonce=(?<ref>[0-9]+)&.*");
        Matcher m = p.matcher(this.url.getQuery());
        m.matches();
        this.fields.put(AdField.ProviderId, m.group("ref"));
        this.fields.put(AdField.Title, wd.getTitle());
        this.fields.put(AdField.Price, Integer.parseInt((new WebDriverWait(wd, 10)).until(
                ExpectedConditions.presenceOfElementLocated(By.className("prix"))).getText().replaceAll("[^0-9]", "")));
        this.fields.put(AdField.Description, (new WebDriverWait(wd, 10)).until(
                ExpectedConditions.presenceOfElementLocated(By.className("text_description"))).getText());
        this.fields.put(AdField.SubmitterId, (new WebDriverWait(wd, 10)).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//li[strong[contains(text(), \"Réf\")]]"))).getText().replaceAll("Réf\\. : ", ""));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Matcher dateMatcher = Pattern.compile(".*(?<date>[0-9]{2}/[0-9]{2}/[0-9]{4}).*")
                    .matcher((new WebDriverWait(wd, 10)).until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath("//li[strong[contains(text(), \"Date de mise à jour\")]]"))).getText());
            dateMatcher.matches();
            this.fields.put(AdField.Date, format.parse(dateMatcher.group("date")));
        } catch (ParseException e) {
            System.out.println("Impossible d'interpréter la date");
        }

        try {
            String energy = (new WebDriverWait(wd, 3)).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//p[strong[contains(text(), \"Classe énergetique\")]]/span"))).getText();
            if (!energy.equals("Non disponible"))
                this.fields.put(AdField.Energy, energy);
        } catch (TimeoutException e) {
            System.out.println("Pas de classe énergie trouvée.");
        }

        try {
            String ghg = (new WebDriverWait(wd, 3)).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//p[strong[contains(text(), \"Emission de gaz à effet de serre\")]]/span"))).getText();
            if (!ghg.equals("Non disponible"))
                this.fields.put(AdField.GHG, ghg);
        } catch (TimeoutException e) {
            System.out.println("Pas de classe EGS trouvée");
        }

        Pattern areaPattern = Pattern.compile(".* (?<area>[0-9]+)(\\.[0-9]+)? m2");
        try {
            Matcher areaMatcher = areaPattern.matcher((new WebDriverWait(wd, 3)).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//li[strong[contains(text(), \"Surface habitable\")]]"))).getText());
            areaMatcher.matches();
            this.fields.put(AdField.Area, Integer.parseInt(areaMatcher.group("area")));
        } catch (TimeoutException e) {
            try {
                Matcher areaMatcher = areaPattern.matcher((new WebDriverWait(wd, 3)).until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//li[strong[contains(text(), \"Superficie\")]]"))).getText());
                areaMatcher.matches();
                this.fields.put(AdField.Area, Integer.parseInt(areaMatcher.group("area")));
            } catch (TimeoutException e2) {
                System.out.println("Pas de surface trouvée");
            }
        }

        // Get pictures
        // Click on the picture to open the carousel
        Vector<String> pictures = new Vector<>();

        (new WebDriverWait(wd, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("visuel_grd"))).click();
        for (WebElement img : (new WebDriverWait(wd, 10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"carousel-inner\"]/div/div/img")))) {
            try {
                URL imgUrl = new URL(img.getAttribute("src"));
                m = Pattern.compile("^/([^/]+/)*(?<imgName>[a-zA-Z0-9_]+\\.[a-zA-Z0-9]{3})$").matcher(imgUrl.getPath());
                m.matches();
                String imgName = m.group("imgName");

                File imgFile = new File(this.fields.get(AdField.Provider) + "/" + this.fields.get(AdField.ProviderId) + "/" + imgName);
                if (!imgFile.exists()) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(imgUrl);
                        imgFile.mkdirs();
                        ImageIO.write(bufferedImage, imgFile.getName().substring(imgName.lastIndexOf(".")+1), imgFile);
                    } catch (IOException e) {
                        System.out.println("Impossible de lire l'image: " + imgUrl.toString());
                    }
                }
                pictures.add(imgFile.getPath());

            } catch (MalformedURLException e) {
                System.out.println("Cannot retrieve url: " + img.getAttribute("src"));
            }

            if (pictures.size() > 0)
                this.fields.put(AdField.Pictures, pictures);
        }

        wd.quit();
        return this;
    }
}
