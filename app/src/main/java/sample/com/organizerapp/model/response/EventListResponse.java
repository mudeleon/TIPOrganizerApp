package sample.com.organizerapp.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Event;


/**
 * @author pocholomia
 * @since 12/4/2016
 */

public class EventListResponse {



    @SerializedName(Constants.DATA)
    private List<Event> data;



    @SerializedName("meta")
    private Meta meta;



    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
