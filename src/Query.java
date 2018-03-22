import java.util.EnumMap;
import java.util.Map;

public class Query {
    enum Keys {
        Name,
        Type,
        Location,
        Zip,
        MinPrice,
        MaxPrice,
        MinArea,
        MaxArea,
        MinRoom,
        MaxRoom,
    }

    EnumMap fields = new EnumMap(Keys.class);

    // Constructor
    Query() { }

}
