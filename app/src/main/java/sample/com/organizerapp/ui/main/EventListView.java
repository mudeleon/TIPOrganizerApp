package sample.com.organizerapp.ui.main;

import com.hannesdorfmann.mosby.mvp.MvpView;

import sample.com.organizerapp.model.data.Event;



public interface EventListView extends MvpView {








    void setEventList();

    void showEventListDetails(Event eventReservation);

    void stopRefresh();

    void showError(String message);




}
