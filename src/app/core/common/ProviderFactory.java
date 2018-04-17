package app.core.common;

import app.core.immonotaires.ImmoNotairesFactory;
import app.core.leboncoin.LeBonCoinFactory;
import app.core.ouestfranceimmo.OuestFranceImmoFactory;

import org.openqa.selenium.WebDriver;

public abstract class ProviderFactory {
    public enum Providers {
        LeBonCoin ("Le Bon Coin"),
        OuestFranceImmo ("Ouest-France Immo"),
        ImmoNotaires ("Immobilier Notaires");

        private final String name;

        Providers(String name) {
            this.name = name;
        }

        public String getName() { return this.name; }
    }

    public ProviderFactory() { }

    static public ProviderFactory createFactory(Providers p){
        switch (p) {
            case LeBonCoin:
                return new LeBonCoinFactory();
            case OuestFranceImmo:
                return new OuestFranceImmoFactory();
            case ImmoNotaires:
                return new ImmoNotairesFactory();
            default:
                return null;
        }
    }

    abstract public ResultPage createResultPage(WebDriver wd);

    abstract public FormPage createFormPage(WebDriver wd);

    abstract public Ad createAd(String url);
}
