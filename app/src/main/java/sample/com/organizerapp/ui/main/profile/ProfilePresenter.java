package sample.com.organizerapp.ui.main.profile;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.com.organizerapp.app.App;
import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.model.response.BasicResponse;
import sample.com.organizerapp.model.response.EventListResponse;

import static com.google.android.gms.internal.zzs.TAG;


@SuppressWarnings("ConstantConditions")
public class ProfilePresenter extends MvpBasePresenter<ProfileView> {

    public void changePassword(String apiToken, String emailAddress, String oldPassword, String newPassword) {
//        getView().showProgress();
//        App.getInstance().getApiInterface().changePassword(apiToken, emailAddress, oldPassword, newPassword)
//                .enqueue(new Callback<BasicResponse>() {
//                    @Override
//                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
//                        getView().stopProgress();
//                        if (response.isSuccessful()) {
//                            getView().showAlert(response.body().getMessage());
//                        } else {
//                            getView().showAlert("Server Error");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BasicResponse> call, Throwable t) {
//                        getView().stopProgress();
//                        Log.e(TAG, "API Call Failure", t);
//                        getView().showAlert("Error Calling API");
//                    }
//                });
    }



    public  void editProfile(String name,String email,String desc, String address, String contact,String apiToken){

        App.getInstance().getApiInterface().updateProfile(name,email,desc,address,contact,Constants.BEARER+apiToken, Constants.APPJSON)
                .enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                        if (response.isSuccessful()) {
                            getView().showAlert(response.body().getMessage());
                        } else {
                            getView().showAlert(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }



}
