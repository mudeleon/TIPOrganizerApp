package sample.com.organizerapp.ui.event;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sample.com.organizerapp.ui.event.attendee.AttendeeActivity;
import sample.com.organizerapp.ui.event.detail.EventDetailActivity;
import sample.com.organizerapp.ui.event.raffle.RaffleActivity;


/**
 * @author pocholomia
 * @since 25/10/2016
 */
class EventTabAdapter extends FragmentStatePagerAdapter {

    private static final String[] TITLES = {"Details", "Attendee","Raffle"};

    public EventTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EventDetailActivity();
            case 1:
                return new AttendeeActivity();

            case 2:
            return new RaffleActivity();


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
