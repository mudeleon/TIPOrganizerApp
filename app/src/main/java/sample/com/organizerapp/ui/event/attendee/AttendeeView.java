package sample.com.organizerapp.ui.event.attendee;

import com.hannesdorfmann.mosby.mvp.MvpView;

import sample.com.organizerapp.model.data.Attendee;


public interface AttendeeView extends MvpView {








    void setAttendeeList();


    void showShare();

    void setNotification();


    void showAttendeeDetails(Attendee attendeeDetails);

    void stopRefresh();

    void showError(String message);

    void showReturn(String message);




}
