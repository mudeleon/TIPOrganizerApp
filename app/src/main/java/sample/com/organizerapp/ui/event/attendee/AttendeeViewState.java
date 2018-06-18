package sample.com.organizerapp.ui.event.attendee;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class AttendeeViewState implements RestorableViewState<AttendeeView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<AttendeeView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(AttendeeView view, boolean retained) {

    }
}
