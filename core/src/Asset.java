import java.net.URL;
import java.util.Date;
import java.util.Vector;

public class Asset {
    // Properties
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    private int price;
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    private Date date;
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    private URL url;
    public URL getUrl() {
        return url;
    }
    public void setUrl(URL url) {
        this.url = url;
    }

    private String description;
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    private AssetType type;
    public AssetType getType() {
        return type;
    }
    public void setType(AssetType type) {
        this.type = type;
    }

    private int nbRooms;
    public int getNbRooms() {
        return nbRooms;
    }
    public void setNbRooms(int nbRooms) {
        this.nbRooms = nbRooms;
    }

    private int area;
    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        this.area = area;
    }

    private String GES;
    public String getGES() {
        return GES;
    }
    public void setGES(String GES) {
        this.GES = GES;
    }

    private String energyClass;
    public String getEnergyClass() {
        return energyClass;
    }
    public void setEnergyClass(String energyClass) {
        this.energyClass = energyClass;
    }

    private Vector<String> pictures = new Vector<>();
    public void addPicture(String picture) {
        pictures.add(picture);
    }

    // Constructor
    public Asset(){}

    public Asset(String name, int price, URL url){
        this.name = name;
        this.price = price;
        this.url = url;
    }

}
