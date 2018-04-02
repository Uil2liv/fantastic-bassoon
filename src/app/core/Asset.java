package app.core;

import app.core.common.Ad;

import java.util.EnumMap;

public class Asset extends EnumMap<Ad.AdField, Object>{
    public Asset() {
        super(Ad.AdField.class);
    }

    public void add(Ad.AdField field, Object value) {
        this.put(field, value);
    }

    public int getAveragePrice() {
        return (int)this.get(Ad.AdField.Price)/(int)this.get(Ad.AdField.Area);
    }
}
