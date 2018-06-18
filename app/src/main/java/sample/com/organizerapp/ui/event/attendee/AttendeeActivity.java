package sample.com.organizerapp.ui.event.attendee;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.Random;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import sample.com.organizerapp.R;
import sample.com.organizerapp.databinding.ActivityEventAttendeeBinding;
import sample.com.organizerapp.databinding.DialogAttendeeProfileBinding;
import sample.com.organizerapp.model.data.Attendee;
import sample.com.organizerapp.model.data.Token;
import sample.com.organizerapp.model.data.User;
import sample.com.organizerapp.util.AnyOrientationCaptureActivity;
import sample.com.organizerapp.util.AppSettings;
import sample.com.organizerapp.util.CircleTransform;

import static android.app.Activity.RESULT_OK;


public class AttendeeActivity
        extends MvpViewStateFragment<AttendeeView, AttendeePresenter>
        implements SwipeRefreshLayout.OnRefreshListener, AttendeeView {

    private static final String TAG = AttendeeActivity.class.getSimpleName();
    private ActivityEventAttendeeBinding binding;
    private Realm realm;
    private User user;
    private Token token;
    private RealmResults<Attendee> attendeeRealmResults;
    private String searchText;
    public String id;
    private String eventID;
    private AppSettings appSettings;
    private AttendeeAdapter attendeeListAdapter;
    private final int PERMISSION_CODE = 9235;
    private static final int MY_PERMISSIONS_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private SupportMapFragment mapFragment;
    private DialogAttendeeProfileBinding dialogBinding;



    public AttendeeActivity(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<AttendeeView> createViewState() {
        setRetainInstance(true);
        return new AttendeeViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_event_attendee, container, false);
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
            //  finish();
        }
       attendeeListAdapter = new AttendeeAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(attendeeListAdapter);

        presenter.onStart();

        int Permission_All = 1;
        String[] Permissions = { Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(!hasPermissions(getActivity(), Permissions)){
            ActivityCompat.requestPermissions(getActivity(), Permissions, Permission_All);
        }
      //  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        appSettings = AppSettings.getAppSettingsFromSharedPreference(getContext());
        eventID = appSettings.getEventDetailId();

        presenter.loadAttendeeList(eventID,token.getToken());

        attendeeListAdapter = new AttendeeAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(attendeeListAdapter);


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



        binding.attendeeScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startScan();
            }
        });




        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


            @Override
            public boolean onQueryTextSubmit(String query) {

                    searchText = query;
                    prepareList();

                    return true;

            }
        });




        binding.attendeeTotal.setText(String.valueOf(presenter.getTotal()));
        binding.attendeePresent.setText(String.valueOf(presenter.getPresent()));
        binding.attendeeNotPresent.setText(String.valueOf(presenter.getNotPresent()));


        }


    @NonNull
    @Override
    public AttendeePresenter createPresenter() {
        return new AttendeePresenter();
    }

//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//        inflater.inflate(R.menu.main, menu);
//        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchText = newText;
//                //prepareList();
//                return true;
//            }
//        });
//
//    }

    private void prepareList() {
        if (attendeeRealmResults.isLoaded() && attendeeRealmResults.isValid()) {
            if (searchText.isEmpty()) {


                attendeeRealmResults = realm.where(Attendee.class).findAllAsync();
                attendeeListAdapter.setAttendeeResult(realm.copyToRealmOrUpdate(attendeeRealmResults.where()
                        .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
                attendeeListAdapter.notifyDataSetChanged();

            } else {

                attendeeRealmResults = realm.where(Attendee.class).findAllAsync();
                attendeeListAdapter.setAttendeeResult(realm.copyToRealmOrUpdate(attendeeRealmResults.where()
                        .contains("emailAddress",searchText, Case.INSENSITIVE)
                        .or()
                        .contains("firstName",searchText, Case.INSENSITIVE)
                        .or()
                        .contains("lastName",searchText, Case.INSENSITIVE)
                        .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
                attendeeListAdapter.notifyDataSetChanged();
            }
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//               getActivity().onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }


    @Override
    public void onDestroy() {
        presenter.onStop();
        attendeeRealmResults.removeChangeListeners();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {


        binding.attendeeTotal.setText(String.valueOf(presenter.getTotal()));
        binding.attendeePresent.setText(String.valueOf(presenter.getPresent()));
        binding.attendeeNotPresent.setText(String.valueOf(presenter.getNotPresent()));

            presenter.loadAttendeeList(eventID,token.getToken());


    }


    public void loadData()
    {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        attendeeRealmResults = realm.where(Attendee.class).findAll();



            if (attendeeRealmResults.isLoaded() && attendeeRealmResults.isValid()) {
                getMvpView().setAttendeeList();

            }else
            {
                presenter.loadAttendeeList(eventID,token.getToken());

            }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setAttendeeList(){

        attendeeRealmResults = realm.where(Attendee.class).findAllAsync();
       attendeeListAdapter.setAttendeeResult(realm.copyToRealmOrUpdate(attendeeRealmResults.where()
               .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        attendeeListAdapter.notifyDataSetChanged();
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
    public void showReturn(String message) {



        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        loadData();
    }



    @Override
    public void showShare(){

    }

    @Override
    public void setNotification(){

    }


    @Override
    public void showAttendeeDetails(final Attendee attendee) {


        dialogBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.dialog_attendee_profile, null, false);
        final AlertDialog alert = new AlertDialog.Builder(getActivity())
                .create();
        alert.setCancelable(true);
        alert.setView(dialogBinding.getRoot(),0,0,0,0);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBinding.setProfile(attendee);
        dialogBinding.setView(getMvpView());
        int pictureSwitcher;

        int min = 1;
        int max = 2;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        if(i1==1)
            pictureSwitcher = R.drawable.ic_profile_m;
        else
            pictureSwitcher = R.drawable.ic_profile_g;


        Glide.with(this)
                .load(pictureSwitcher)
                .transform(new CircleTransform(getActivity()))
                .into(dialogBinding.imageRunnerProfile);


        if(attendee.getStatus().equals("1"))
        {
            Glide.with(this)
                    .load(R.drawable.ic_attendance_check)
                    .transform(new CircleTransform(getActivity()))
                    .into(dialogBinding.attendeeStatusDetail);

            dialogBinding.attendeeStatusDetailCard.setCardBackgroundColor(getContext().getResources().getColor(R.color.greenSuccessDark));


            dialogBinding.attendeeProfileMark.setVisibility(View.GONE);
        }else
        {

            dialogBinding.attendeeProfileMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    presenter.markAttendee(eventID,attendee.getId(),token.getToken());
                    alert.dismiss();

                }
            });

        }


        dialogBinding.runnerProfileClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ScanBar (View view ) {
        requestScan();
    }

    // fucntion to scan barcode
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestScan(){
//        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
//        }else{
//            startScan();
//        }
    }


    public void startScan(){

        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan Order Code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.forSupportFragment(AttendeeActivity.this).initiateScan();
        //integrator.initiateScan();

    }

    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }


    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent in ) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode,in);


        if( requestCode == IntentIntegrator.REQUEST_CODE ){


            if( resultCode == RESULT_OK ) {

                try {
                    String contents = in.getStringExtra("SCAN_RESULT");




                     Attendee  selectedAttendee    = presenter.getAttendee((contents));
                   // Log.d("SASAS>>>>",selectedAttendee.getFirstName());
                     presenter.markAttendee(eventID,selectedAttendee.getId(),token.getToken());


                     startScan();

                } catch (Exception e) {


                    Log.d(">>>>>",""+e);
                    showError("Code Does Not Match!");
                }

            }

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan();
            }
            else {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }

        switch (requestCode) {

            // other 'case' lines to check for other
            // permissions this app might request
            case MY_PERMISSIONS_REQUEST:{
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }else{

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }

        }
    }




}
