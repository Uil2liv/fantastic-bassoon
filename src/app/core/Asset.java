package app.core;

import app.core.common.Ad;

import java.util.Vector;

public class Asset extends Vector<Ad> {
    public Asset() {
        super();
        this.status = Status.New;
    }

    public Asset(Ad ad) {
        this();
        this.add(ad);
    }

    public Status status;

    public Object get(Ad.AdField key) {
        for (Ad ad : this) {
            if (ad.get(key) != null)
                return ad.get(key);
        }

        return null;
    }

    public Ad getAd(Ad requestedAd) {
        for (Ad ad : this){
            if (ad.get(Ad.AdField.ProviderId).equals(requestedAd.get(Ad.AdField.ProviderId)))
                return ad;
        }

        return null;
    }

    public void update(Ad updatedAd){
        for (Ad ad : this) {
            if (updatedAd.get(Ad.AdField.ProviderId) != null &
                    updatedAd.get(Ad.AdField.ProviderId).equals(ad.get(Ad.AdField.ProviderId))) {
                if (!ad.isEquals(updatedAd)) {
                    this.remove(ad);
                    this.add(updatedAd);
                    this.status = Status.Updated;
                }
            }
        }
    }

    public int getAveragePrice() {
        for (Ad ad : this) {
            if (ad.get(Ad.AdField.Area) != null) {
                return (Integer)ad.get(Ad.AdField.Price) / (Integer)ad.get(Ad.AdField.Area);
            }
        }

        return 0;
    }

    enum Status {
        New,
        Updated,
        Unchanged,
        Archived
    }
}
