package sample.com.organizerapp.model.response;

import com.google.gson.annotations.SerializedName;

import sample.com.organizerapp.app.Constants;


/**
 * @author pocholomia
 * @since 12/4/2016
 */

public class MarkAttendeeResponse {


    @SerializedName(Constants.MESSAGE)
    private String message;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
