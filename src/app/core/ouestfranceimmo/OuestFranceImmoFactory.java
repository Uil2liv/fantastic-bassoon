package app.core.ouestfranceimmo;

import app.core.common.Ad;
import app.core.common.FormPage;
import app.core.common.ProviderFactory;
import app.core.common.ResultPage;
import org.openqa.selenium.WebDriver;

public class OuestFranceImmoFactory extends ProviderFactory {
    @Override
    public ResultPage createResultPage(WebDriver wd) {
        return new OuestFranceImmoResultPage(wd, this);
    }

    @Override
    public FormPage createFormPage(WebDriver wd) {
        return new OuestFranceImmoFormPage(wd, this);
    }

    @Override
    public Ad createAd(String url) {
        return new OuestFranceImmoAd(url);
    }
}
