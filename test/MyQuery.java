import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.EnumMap;

//@JsonSerialize(using = QuerySerializer.class)
public class MyQuery extends EnumMap<MyQuery.Keys, Object>{
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

    // Constructor
    MyQuery() { super(Keys.class); }

    public void add(Keys k, Object value) {
        this.put(k, value);
    }

    // Methods
    public String toString() { return (String)this.get(Keys.Name);}
}
