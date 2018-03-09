public class Query {
    // Properties
    private AssetType type;
    public AssetType getType(){
        return type;
    }

    private String location;
    public String getLocation(){
        return location;
    }

    private String zip;
    public String getZipCode(){
        return zip;
    }

    // Constructor
    public Query(AssetType assetType, String location, String zipCode){
        this.type=assetType;
        this.location=location;
        this.zip=zipCode;
    }
}
