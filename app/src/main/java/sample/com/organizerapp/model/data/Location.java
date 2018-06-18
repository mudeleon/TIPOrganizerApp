package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Location extends RealmObject {




    //    @PrimaryKey
//    @SerializedName("id")
//    private int id;
    @SerializedName("name")
    private String locationName;
    @SerializedName("address")
    private String locationAddress;
    @SerializedName("lat")
    private String locationLat;
    @SerializedName("lng")
    private String locationgLong;

    public Location() {
    }





    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationgLong() {
        return locationgLong;
    }

    public void setLocationgLong(String locationgLong) {
        this.locationgLong = locationgLong;
    }





}
