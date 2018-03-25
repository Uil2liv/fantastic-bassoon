import java.util.Vector;

public class Provider {
    protected FormPage formPage;
    protected ProviderFactory factory;

    public Provider(ProviderFactory.Providers provider){
        this.factory = ProviderFactory.createFactory(provider);
    }

    public Vector<Asset> search(Query q){
        formPage = factory.createFormPage();
        formPage.access();
        formPage.fill(q);
        ResultPage resultPage = formPage.submit();
        return resultPage.getAssets();
    }
}
