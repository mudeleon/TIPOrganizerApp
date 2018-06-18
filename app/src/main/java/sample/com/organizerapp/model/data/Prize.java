package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Prize extends RealmObject {




    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("priceName")
    private String priceName;
    @SerializedName("prizeQuantity")
    private String prizeQuantity;
    @SerializedName("priceImage")
    private String priceImage;
    @SerializedName("priceDescription")
    private String priceDescription;
    @SerializedName("priceTimestamp")
    private String priceTimestamp;




    private  Boolean clickStatus;




    public Prize() {
    }



    public Boolean getClickStatus() {
        return clickStatus;
    }

    public void setClickStatus(Boolean clickStatus) {
        this.clickStatus = clickStatus;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getPrizeQuantity() {
        return prizeQuantity;
    }

    public void setPrizeQuantity(String prizeQuantity) {
        this.prizeQuantity = prizeQuantity;
    }

    public String getPriceImage() {
        return priceImage;
    }

    public void setPriceImage(String priceImage) {
        this.priceImage = priceImage;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }








}
