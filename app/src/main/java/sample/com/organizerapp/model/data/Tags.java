package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Tags extends RealmObject {


    public String getTagsTitle() {
        return tagsTitle;
    }

    public void setTagsTitle(String tagsTitle) {
        this.tagsTitle = tagsTitle;
    }

    //    @PrimaryKey
//    @SerializedName("id")
//    private int id;
    @SerializedName("title")
    private String tagsTitle;

    public Tags() {
    }











}
