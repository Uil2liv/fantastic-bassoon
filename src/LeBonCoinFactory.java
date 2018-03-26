public class LeBonCoinFactory extends ProviderFactory {
    @Override
    public ResultPage createResultPage() {
        return new LeBonCoinResultPage(wd, this);
    }

    @Override
    public FormPage createFormPage() {
        return new LeBonCoinFormPage(wd, this);
    }

    @Override
    public Ad createAd(String url) {
        return new LeBonCoinAd(url);
    }
}
