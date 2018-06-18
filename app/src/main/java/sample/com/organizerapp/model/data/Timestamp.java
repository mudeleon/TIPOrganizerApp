package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Timestamp extends RealmObject {




    //    @PrimaryKey
//    @SerializedName("id")
//    private int id;
    @SerializedName("timestamp_start")
    private String timestampStart;


    @SerializedName("timestamp_end")
    private String timestampEnd;


    public Timestamp() {
    }



    public String getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(String timestampStart) {
        this.timestampStart = timestampStart;
    }

    public String getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(String timestampEnd) {
        this.timestampEnd = timestampEnd;
    }








}
