package sample.com.organizerapp.ui.event.raffle;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class RaffleViewState implements RestorableViewState<RaffleView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<RaffleView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(RaffleView view, boolean retained) {

    }
}
