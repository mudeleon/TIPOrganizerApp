package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Cursor extends RealmObject {



    //    @PrimaryKey
//    @SerializedName("id")
//    private int id;
    @SerializedName("current")
    private String current;

    @SerializedName("prev")
    private String previous;

    @SerializedName("next")
    private String next;

    @SerializedName("count")
    private String count;

    public Cursor() {
    }


    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }










}
