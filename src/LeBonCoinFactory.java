public class LeBonCoinFactory extends ProviderFactory {
    @Override
    public ResultPage createResultPage() {
        return new LeBonCoinResultPage(wd, this);
    }

    @Override
    public FormPage createFormPage() {
        return new LeBonCoinFormPage(wd, this);
    }
}
