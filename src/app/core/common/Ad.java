package app.core.common;


import java.util.EnumMap;

public abstract class Ad extends Page {
    protected EnumMap<AdField, Object> fields = new EnumMap<>(AdField.class);

    public Ad(String url) {
        super(url);
    }

    public Object get(AdField key) { return this.fields.get(key); }

    public Boolean isEquals(Ad ad){
        for (AdField key : AdField.values()) {
            if (this.get(key) != ad.get(key))
                return false;
        }
        return true;
    }

    public abstract void getFields();

    public enum AdField {
        Id,
        Title,
        Price,
        Date,
        Type,
        NbRooms,
        Area,
        ProviderId,
        SubmitterId,
        Description,
        GHG,
        Energy,
        Submitter,
    }
}
