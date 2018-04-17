package app.core.immonotaires;

import app.core.common.Ad;
import app.core.common.FormPage;
import app.core.common.ProviderFactory;
import app.core.common.ResultPage;
import org.openqa.selenium.WebDriver;

public class ImmoNotairesFactory extends ProviderFactory{

    @Override
    public ResultPage createResultPage(WebDriver wd) {
        return new ImmoNotairesResultPage(wd, this);
    }

    @Override
    public FormPage createFormPage(WebDriver wd) {
        return new ImmoNotairesFormPage(wd, this);
    }

    @Override
    public Ad createAd(String url) {
        return new ImmoNotairesAd(url);
    }
}
