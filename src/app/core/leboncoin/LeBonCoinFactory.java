package app.core.leboncoin;

import app.core.common.Ad;
import app.core.common.FormPage;
import app.core.common.ProviderFactory;
import app.core.common.ResultPage;
import org.openqa.selenium.WebDriver;

public class LeBonCoinFactory extends ProviderFactory {
    @Override
    public ResultPage createResultPage(WebDriver wd) {
        return new LeBonCoinResultPage(wd, this);
    }

    @Override
    public FormPage createFormPage(WebDriver wd) {
        return new LeBonCoinFormPage(wd, this);
    }

    @Override
    public Ad createAd(String url) {
        return new LeBonCoinAd(url);
    }
}
