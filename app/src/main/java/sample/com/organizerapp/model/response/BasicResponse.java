package sample.com.organizerapp.model.response;

import com.google.gson.annotations.SerializedName;

import sample.com.organizerapp.app.Constants;


/**
 * @author pocholomia
 * @since 12/4/2016
 */

public class BasicResponse {

    @SerializedName(Constants.SUCCESS)
    private boolean success;
    @SerializedName(Constants.MESSAGE)
    private String message;


    @SerializedName(Constants.DATA)
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
