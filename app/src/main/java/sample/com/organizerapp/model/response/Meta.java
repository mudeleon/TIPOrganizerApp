package sample.com.organizerapp.model.response;



import com.google.gson.annotations.SerializedName;

import java.util.List;

import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Cursor;
import sample.com.organizerapp.model.data.Event;


/**
 * @author pocholomia
 * @since 12/4/2016
 */

public class Meta {



    @SerializedName("cursor")
    private Cursor cursor;


    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

}
