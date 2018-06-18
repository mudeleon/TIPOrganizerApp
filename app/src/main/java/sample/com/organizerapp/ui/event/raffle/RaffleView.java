package sample.com.organizerapp.ui.event.raffle;

import com.hannesdorfmann.mosby.mvp.MvpView;

import sample.com.organizerapp.model.data.Prize;


public interface RaffleView extends MvpView {








    void prizeDelete(Prize prize);

    void prizeEdit(Prize prize);

    void setPrizeList();


    void showPrizeDetails(Prize prize);

    void stopRefresh();

    void showError(String message);





}
