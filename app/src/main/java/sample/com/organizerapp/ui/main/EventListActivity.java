package sample.com.organizerapp.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
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


import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import io.realm.Realm;
import io.realm.RealmResults;
import sample.com.organizerapp.R;
import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.databinding.ActivityEventListBinding;
import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.model.data.Token;
import sample.com.organizerapp.model.data.User;
import sample.com.organizerapp.app.App;
import sample.com.organizerapp.ui.event.EventActivity;
import sample.com.organizerapp.util.AppSettings;


public class EventListActivity
        extends MvpViewStateFragment<EventListView, EventListPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, EventListView {

    private static final String TAG = EventListActivity.class.getSimpleName();
    private ActivityEventListBinding binding;
    private Realm realm;
    private User user;
    private Token token;
    private RealmResults<Event> eventsRealmResults;
    private EventListAdapter eventListAdapter;
    private String searchText;
    public String id;
    AppSettings appSettings;

    public EventListActivity(){

    }


    public static EventListActivity newInstance() {
        EventListActivity fragment = new EventListActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }





    @NonNull
    @Override
    public ViewState<EventListView> createViewState() {
        setRetainInstance(true);
        return new EventListViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        binding.swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                binding.swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_event_list, container, false);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        searchText = "";

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
        token =realm.where(Token.class).findFirst();

        if (user == null) {
            Log.d(TAG, "No User found");
              //finish();
        }

      //  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Organizer App");

        eventListAdapter = new EventListAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(eventListAdapter);





        //binding.swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_refresh_layout_color_scheme));
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
       // binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                binding.swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });









    }


    @NonNull
    @Override
    public EventListPresenter createPresenter() {
        return new EventListPresenter();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                //prepareList();
                return true;
            }
        });

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
            case android.R.id.home:
               getActivity().onBackPressed();
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
       // eventsRealmResults.removeChangeListeners();
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
        eventsRealmResults = realm.where(Event.class).findAll();


           // if (eventsRealmResults.isLoaded() && eventsRealmResults.isValid()) {
           if(eventsRealmResults.size()>0){
                getMvpView().setEventList();


            }else
            {
                presenter.loadEventList(token.getToken());

            }


    }

    @Override
    public void setEventList(){



        eventsRealmResults = realm.where(Event.class).findAllAsync();
        eventListAdapter.setEventResult(realm.copyToRealmOrUpdate(eventsRealmResults.where()
                .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        eventListAdapter.notifyDataSetChanged();



        if(eventListAdapter.getItemCount()==0)
        {
            binding.eventlistNoRecyclerView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
    }



    @Override
    public void stopRefresh() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }







    @Override
    public void showEventListDetails(Event event) {

        appSettings = AppSettings.getAppSettingsFromSharedPreference(getContext());
        appSettings.setEventDetailId(event.getId());
        appSettings.setEventDetailName(event.getEventName());
        appSettings.save(getContext());

        Intent intent = new Intent(getActivity(), EventActivity.class);
//        Bundle extras = new Bundle();
//        extras.putString(Constants.EVENT_DETAILS_ID, event.getId());
//        extras.putString(Constants.EVENT_DETAILS_NAME, event.getEventName());
//        intent.putExtras(extras);
        startActivity(intent);
    }




}
