package sample.com.organizerapp.ui.event.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import sample.com.organizerapp.R;
import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.app.Endpoints;
import sample.com.organizerapp.databinding.ActivityEventDetailslBinding;
import sample.com.organizerapp.databinding.ActivityEventListBinding;
import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.model.data.Token;
import sample.com.organizerapp.model.data.User;
import sample.com.organizerapp.util.AppSettings;
import sample.com.organizerapp.util.DateTimeUtils;
import com.google.android.gms.maps.SupportMapFragment;


public class EventDetailActivity
        extends MvpViewStateFragment<EventDetailView, EventDetailPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, EventDetailView,OnMapReadyCallback {

    private static final String TAG = EventDetailActivity.class.getSimpleName();
    private ActivityEventDetailslBinding binding;
    private Realm realm;
    private User user;
    private Token token;
    private Event eventsRealmResults;
    private String searchText;
    public String id;
    private String eventID;
    private AppSettings appSettings;

    private SupportMapFragment mapFragment;



    public EventDetailActivity(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<EventDetailView> createViewState() {
        setRetainInstance(true);
        return new EventDetailViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_event_detailsl, container, false);
        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();
        searchText = "";

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
        token = realm.where(Token.class).findFirst();

        if (user == null) {
            Log.d(TAG, "No User found");
            //  finish();
        }



      //  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        appSettings = AppSettings.getAppSettingsFromSharedPreference(getContext());
        eventID = appSettings.getEventDetailId();




        //binding.swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_refresh_layout_color_scheme));
      //  binding.swipeRefreshLayout.setOnRefreshListener(this);


    }


    @NonNull
    @Override
    public EventDetailPresenter createPresenter() {
        return new EventDetailPresenter();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);

    }

    /*private void prepareList() {
        if (eventRealmResults.isLoaded() && eventRealmResults.isValid()) {
            if (searchText.isEmpty()) {
                getMvpView().setData(realm.copyFromRealm(eventRealmResults));
            } else {
                getMvpView().setData(realm.copyToRealmOrUpdate(eventRealmResults.where()
                        .contains("eventName", searchText, Case.INSENSITIVE)
                        .or()
                        .contains("tags", searchText, Case.INSENSITIVE)
                        .findAll()));
            }
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_event:


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }


    @Override
    public void onDestroy() {
        eventsRealmResults.removeChangeListeners();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {

            presenter.loadEventList(token.getToken());


    }


    public void loadData()
    {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        eventsRealmResults = realm.where(Event.class).contains("id",eventID).findFirst();



            if (eventsRealmResults.isLoaded() && eventsRealmResults.isValid()) {
                getMvpView().setEventList();
            }else
            {
                presenter.loadEventList(token.getToken());
            }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

    }

    @Override
    public void setEventList(){

        eventsRealmResults = realm.where(Event.class).contains("id",eventID).findFirst();
        binding.setEvent(eventsRealmResults);



        Glide.with(EventDetailActivity.this)
                .load(Endpoints.EVENT_URL_IMAGE+eventsRealmResults.getEventImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .error(R.drawable.sample_event2)
                .into(binding.eventDetailImage);





        String contactString = "";
        for (int a=0;a<eventsRealmResults.getEventContact().size();a++)
        {
            contactString += eventsRealmResults.getEventContact().get(a).getContactNumberList()+" \n";
        }
        binding.eventDetailContact.setText(contactString);

        String timeString = "";
        for (int a=0;a<eventsRealmResults.getEventTimestamp().size();a++)
        {
            timeString += (DateTimeUtils.eventDetail(eventsRealmResults.getEventTimestamp().get(a).getTimestampStart(),eventsRealmResults.getEventTimestamp().get(a).getTimestampStart()))+"\n";
        }
        binding.eventDetailSchedule.setText(timeString);


        List<String> tags = new ArrayList<String>();
        for (int a=0;a<eventsRealmResults.getEventTags().size();a++)
        {
            tags.add(eventsRealmResults.getEventTags().get(a).getTagsTitle());
        }
        binding.tagGroup.setTags(tags);

      binding.eventDetailAddress.setText(eventsRealmResults.getEventLocation().get(0).getLocationName()+"\n"+eventsRealmResults.getEventLocation().get(0).getLocationAddress());


        mapFragment.getMapAsync(this);




//        eventListAdapter.setEventResult(realm.copyToRealmOrUpdate(eventsRealmResults.where()
//                .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
//        eventListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
     LatLng sydney = new LatLng(Double.parseDouble(eventsRealmResults.getEventLocation().get(0).getLocationLat()),Double.parseDouble(eventsRealmResults.getEventLocation().get(0).getLocationgLong()));
   googleMap.addMarker(new MarkerOptions().position(sydney).title(eventsRealmResults.getEventLocation().get(0).getLocationName() != null ? eventsRealmResults.getEventLocation().get(0).getLocationName() : ""));
     CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
     googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    @Override
    public void stopRefresh() {
        //binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void showShare(){

    }

    @Override
    public void setNotification(){

    }


    @Override
    public void showEventListDetails(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putString(Constants.EVENT_DETAILS_ID, event.getId());
        extras.putString(Constants.EVENT_DETAILS_NAME, event.getEventName());
        intent.putExtras(extras);
        startActivity(intent);
    }




}
