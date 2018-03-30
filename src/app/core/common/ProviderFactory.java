package app.core.common;

import app.core.leboncoin.LeBonCoinFactory;

import org.openqa.selenium.WebDriver;

public abstract class ProviderFactory {
    public enum Providers {
        LeBonCoin
    }

    public ProviderFactory() { }

    static public ProviderFactory createFactory(Providers p){
        switch (p) {
            case LeBonCoin:
                return new LeBonCoinFactory();
            default:
                return null;
        }
    }

    abstract public ResultPage createResultPage(WebDriver wd);

    abstract public FormPage createFormPage(WebDriver wd);

    abstract public Ad createAd(String url);
}
