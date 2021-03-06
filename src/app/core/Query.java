package app.core;

import java.util.EnumMap;

public class Query extends EnumMap<Query.Keys, Object>{
    public enum Keys {
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
    public Query() { super(Keys.class); }

    public void add(Keys k, Object value) {
        this.put(k, value);
    }

    // Methods
    public String toString() { return (String)this.get(Keys.Name);}
}
