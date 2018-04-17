package app.core;

import app.FantasticBassoon;
import app.core.common.Ad;

import java.util.Vector;

public class Asset extends Vector<Ad> implements FantasticBassoon.Selectable, FantasticBassoon.Mergeable, FantasticBassoon.Removable {
    public Asset() {
        super();
        this.status = Status.New;
    }

    public Asset(Ad ad) {
        this();
        this.add(ad);
    }

    public static void merge(Asset[] assets){
        if (assets.length > 1) {
            for (int i = 1; i < assets.length; i++) {
                assets[0].addAll(assets[i]);
                ((Search)FantasticBassoon.getSelectedSearch()).removeAsset(assets[i]);
            }
        }
    }

    public Status status;

    public Object get(Ad.AdField key) {
        switch (key){
            case SubmitterId:
                Vector<String> submitterIds = new Vector<>();
                for (Ad ad : this)
                    if (ad.get(Ad.AdField.SubmitterId) != null)
                        submitterIds.add((String)ad.get(Ad.AdField.SubmitterId));
                return submitterIds;
            case Pictures:
                Vector<String> pictures = new Vector<>();
                for (Ad ad : this)
                    if (ad.get(key) != null)
                        pictures.addAll((Vector<String>)ad.get(key));
                return pictures;
            default:
                for (Ad ad : this) {
                    if (ad.get(key) != null)
                        return ad.get(key);
                }
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

    // Implements Removable
    public void remove() {
        ((Search)FantasticBassoon.getSelectedSearch()).removeAsset(this);
    }

    enum Status {
        New,
        Updated,
        Unchanged,
        Archived
    }
}
