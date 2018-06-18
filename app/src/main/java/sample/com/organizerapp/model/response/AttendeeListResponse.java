package sample.com.organizerapp.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Attendee;
import sample.com.organizerapp.model.data.Event;


/**
 * @author pocholomia
 * @since 12/4/2016
 */

public class AttendeeListResponse {


    @SerializedName(Constants.DATA)
    private List<Attendee> data;



    @SerializedName("meta")
    private Meta meta;



    public List<Attendee> getData() {
        return data;
    }

    public void setData(List<Attendee> data) {
        this.data = data;
    }



    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
