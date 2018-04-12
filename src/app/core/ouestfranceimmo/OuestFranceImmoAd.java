package app.core.ouestfranceimmo;

import app.core.common.Ad;
import app.core.common.ProviderFactory;

public class OuestFranceImmoAd extends Ad {
    public OuestFranceImmoAd(String url) {
        super(url);
        this.fields.put(AdField.Provider, ProviderFactory.Providers.OuestFranceImmo);
    }

    @Override
    public void getFields() {

    }
}
