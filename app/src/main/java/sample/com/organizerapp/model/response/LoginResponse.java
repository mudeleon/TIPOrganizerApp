package sample.com.organizerapp.model.response;


import com.google.gson.annotations.SerializedName;

import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Token;
import sample.com.organizerapp.model.data.User;


public class LoginResponse {

    @SerializedName("organizer")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token_type")
    private String tokenType;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


    @SerializedName("expires_in")
    private String tokenExpire;

    public String getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(String tokenExpire) {
        this.tokenExpire = tokenExpire;
    }


}
