package sample.com.organizerapp.ui.event.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;

import sample.com.organizerapp.model.data.Event;


public interface EventDetailView extends MvpView {








    void setEventList();


    void showShare();

    void setNotification();


    void showEventListDetails(Event eventReservation);

    void stopRefresh();

    void showError(String message);




}
