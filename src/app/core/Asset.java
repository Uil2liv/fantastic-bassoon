package app.core;

import app.core.common.Ad;

import java.util.Vector;

public class Asset extends Vector<Ad> {
    public Asset() {
        super();
    }

    public Object get(Ad.AdField key) {
        for (Ad ad : this) {
            if (ad.get(key) != null)
                return ad.get(key);
        }

        return null;
    }

    public int getAveragePrice() {
        for (Ad ad : this) {
            if (ad.get(Ad.AdField.Area) != null) {
                return (Integer)ad.get(Ad.AdField.Price) / (Integer)ad.get(Ad.AdField.Area);
            }
        }

        return 0;
    }
}
