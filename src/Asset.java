import java.util.EnumMap;

public class Asset {
    private EnumMap<Ad.AdField, String> fields = new EnumMap<>(Ad.AdField.class);

    public void add(Ad.AdField field, String value) {
        this.fields.put(field, value);
    }
}
