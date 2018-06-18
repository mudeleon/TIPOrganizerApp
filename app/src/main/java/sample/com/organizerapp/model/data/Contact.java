package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Contact extends RealmObject {




    //    @PrimaryKey
//    @SerializedName("id")
//    private int id;
    @SerializedName("contacts")
    private String contactNumberList;

    public Contact() {
    }



    public String getContactNumberList() {
        return contactNumberList;
    }

    public void setContactNumberList(String contactNumberList) {
        this.contactNumberList = contactNumberList;
    }







}
