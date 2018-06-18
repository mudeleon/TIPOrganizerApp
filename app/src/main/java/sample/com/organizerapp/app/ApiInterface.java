package sample.com.organizerapp.app;



import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.model.response.AttendeeListResponse;
import sample.com.organizerapp.model.response.BasicResponse;
import sample.com.organizerapp.model.response.EventListResponse;
import sample.com.organizerapp.model.response.LoginResponse;
import sample.com.organizerapp.model.response.MarkAttendeeResponse;


public interface ApiInterface {


    //Credentials
    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    Call<LoginResponse> login(@Field(Constants.EMAIL_ADD) String emailAddress,
                              @Field(Constants.PASSWORD) String password);

    @POST(Endpoints.LOGOUT)
    Call<BasicResponse> logout(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);

    //Profile
    @FormUrlEncoded
    @PUT(Endpoints.PROFILE)
    Call<BasicResponse> updateProfile(@Field(Constants.NAME) String name,@Field(Constants.EMAIL_ADD) String emailAddress,
                                      @Field(Constants.DESCRIPTION) String desc,
                                      @Field(Constants.
                                      ADDRESS) String add,
                                      @Field(Constants.CONTACT) String contact,
                                      @Header(Constants.AUTHORIZATION) String authorization , @Header(Constants.ACCEPT) String json);


//    @GET(Endpoints.FORGOT_PASSWORD)
//    Call<BasicResponse> forgotPassword(@Path("email_address") String email);
//
//    @FormUrlEncoded
//    @POST(Endpoints.REGISTER)
//    Call<BasicResponse> register(@FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @PUT(Endpoints.CHANGE_PASSWORD)
//    Call<BasicResponse> changePassword(@Header(Constants.AUTHORIZATION) String authorization,
//                                       @Field(Constants.EMAIL_ADD) String emailAdd,
//                                       @Field(Constants.OLD_PASSWORD) String oldPassword,
//                                       @Field(Constants.PASSWORD) String password);
//
//



//    //Profile
//    @GET(Endpoints.PROVINCE)
//    Call<List<Province>> getProvince(@Header(Constants.ACCEPT) String json);
//
//    @GET(Endpoints.COUNTRY)
//    Call<List<Country>> getCountry(@Header(Constants.ACCEPT) String json);
//
//    @GET(Endpoints.CITIES)
//    Call<List<CityList>> getCities(@Path("province_id") Integer province_id, @Header(Constants.ACCEPT) String json);
//
//
//    @GET(Endpoints.SECURITY_QUESTION)
//    Call<List<SecurityQuestion>> getSecurityQuestions(@Query("set_number") Integer set_number, @Header(Constants.ACCEPT) String json);
//
//    @GET(Endpoints.SECURITY_QUESTION2)
//    Call<List<SecurityQuestion2>> getSecurityQuestions2(@Query("set_number") Integer set_number, @Header(Constants.ACCEPT) String json);
//
//    @FormUrlEncoded
//    @POST(Endpoints.UPDATE_ACCOUNT)
//    Call<LoginResponse> updateAccount(@Header(Constants.AUTHORIZATION) String authorization,
//                                      @FieldMap Map<String, String> params, @Header(Constants.ACCEPT) String json);
//
//    @Multipart
//    @POST(Endpoints.UPLOAD_IMG)
//    Call<UploadProfileImageResponse> upload(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json
//            , @Part MultipartBody.Part file);







    //Event

    @GET(Endpoints.EVENT_LIST)
    Call<EventListResponse>getEventList(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);



    //Attendee
     @GET(Endpoints.ATTENDEE_LIST)
     Call<AttendeeListResponse> getAttendeeList(@Path("event_id") String event_id,@Header(Constants.AUTHORIZATION) String authorizatrion,@Header(Constants.ACCEPT) String json);

    @PUT(Endpoints.ATTENDEE_MARK)
    Call<MarkAttendeeResponse> markAttendee(@Path("event_id") String event_id, @Path("attendee_id") String attendee_id, @Header(Constants.ACCEPT) String json,@Header(Constants.AUTHORIZATION) String authorization);

}
