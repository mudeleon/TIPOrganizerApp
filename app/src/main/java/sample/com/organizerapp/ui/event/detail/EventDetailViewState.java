package sample.com.organizerapp.ui.event.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class EventDetailViewState implements RestorableViewState<EventDetailView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<EventDetailView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(EventDetailView view, boolean retained) {

    }
}
