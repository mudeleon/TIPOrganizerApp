package sample.com.organizerapp.ui.main.profile;

import com.hannesdorfmann.mosby.mvp.MvpView;

import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.model.data.User;


public interface ProfileView extends MvpView {




    void showError(String message);


    void onChangePasswordClicked();

    void showProgress();

    void stopProgress();

    void showAlert(String message);

    void OnButtonEdit(User user);




}
