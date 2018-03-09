import java.util.List;

public interface IProvider {
    // Methods
    List<Asset> Search(Query q);
}
