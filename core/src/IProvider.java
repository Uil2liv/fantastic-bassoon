import app.core.Asset;
import app.core.common.Query;

import java.util.List;

public interface IProvider {
    // Methods
    List<Asset> Search(Query q);
}
