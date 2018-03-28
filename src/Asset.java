import java.util.EnumMap;

public class Asset {
    private EnumMap<Ad.AdField, Object> fields = new EnumMap<>(Ad.AdField.class);

    public void add(Ad.AdField field, Object value) {
        this.fields.put(field, value);
    }

    public Object get(Ad.AdField key) {
        return fields.get(key);
    }
}
