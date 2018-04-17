package app.core.leboncoin;

import app.FantasticBassoon;
import app.core.AssetType;
import app.core.common.Ad;
import app.core.common.Provider;
import app.core.common.ProviderFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeBonCoinAd extends Ad {
    LeBonCoinAd(String url) {
        super(url);
        this.fields.put(AdField.Provider, ProviderFactory.Providers.LeBonCoin);
    }

    @Override
    public Ad getFields() {
        WebDriver wd = new ChromeDriver();

        wd.get(this.url.toString());

        // Mandatory Fields
        this.fields.put(AdField.Title, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_title\"]/h1")).getText());
        this.fields.put(AdField.Price, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_price\"]/div/span"))
                .getAttribute("innerText").replaceAll("[^0-9]", "")));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");
        try {
            this.fields.put(AdField.Date,dateFormat.parse(wd.findElement(By.xpath("//*[@data-qa-id=\"adview_date\"]")).getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Matcher m = Pattern.compile("/[^/]+/(?<Id>[0-9]+)\\.htm").matcher(this.url.getPath());
        m.matches();
        this.fields.put(AdField.ProviderId, m.group("Id"));

        switch ( wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_real_estate_type\"]/div/div[2]")).getText() ) {
            case "Maison" :
                this.fields.put(AdField.Type, AssetType.House);
                break;
            case "Terrain" :
                this.fields.put(AdField.Type, AssetType.Lot);
                break;
        }

        this.fields.put(AdField.Description, wd.findElement(By.xpath("//*[@data-qa-id=\"adview_description_container\"]/div[1]/span")).getText());

        // Optional fields
        try {
            this.fields.put(AdField.NbRooms, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_rooms\"]/div/div[2]")).getText()));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de nombre de pièces.");
        }

        try {
            this.fields.put(AdField.Area, Integer.parseInt(wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_square\"]/div/div[2]")).getText().replaceAll("[^0-9]", "")));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de surface.");
        }
        try {
            this.fields.put(AdField.SubmitterId, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_custom_ref\"]/div/div[2]")).getText());
        } catch (NoSuchElementException e) {
            System.out.println("Pas de référence de l'émetteur.");
        }
        try {
            this.fields.put(AdField.GHG, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_ges\"]/div/div[2]/div/div[contains(@class, \"_1sd0z\")]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe émission GES.");
        }
        try {
            this.fields.put(AdField.Energy, wd.findElement(By.xpath("//*[@data-qa-id=\"criteria_item_energy_rate\"]/div/div[2]/div/div[contains(@class, \"_1sd0z\")]")).getText().substring(0, 1));
        } catch (NoSuchElementException e) {
            System.out.println("Pas de classe énergie.");
        }

        // Click on the slideshow to display all the pictures
        Vector<String> pictures = new Vector<>();

        wd.findElement(By.xpath("//*[@data-qa-id=\"slideshow_container\"]/div[1]/div[1]/div[1]/div[1]/div[2]/img")).click();
        for (WebElement img : wd.findElements(By.xpath("//*[@data-qa-id=\"lightbox_item\"]/img"))) {
            try {
                URL imgUrl = new URL(img.getAttribute("src"));
                m = Pattern.compile("^/([^/]+/)*(?<imgName>[a-zA-Z0-9]+\\.[a-zA-Z0-9]{3})$").matcher(imgUrl.getPath());
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
