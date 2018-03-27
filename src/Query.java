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

    EnumMap<Query.Keys, Object> fields = new EnumMap<>(Keys.class);

    // Constructor
    Query() { }

    public void add(Keys k, Object value) {
        this.fields.put(k, value);
    }

    // Methods
    public String toString() { return (String)this.fields.get(Keys.Name);}
}
