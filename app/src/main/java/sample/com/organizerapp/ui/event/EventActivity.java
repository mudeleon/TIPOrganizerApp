package sample.com.organizerapp.ui.event;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.com.organizerapp.BuildConfig;
import sample.com.organizerapp.R;
import sample.com.organizerapp.databinding.ActivityEventMainBinding;
import sample.com.organizerapp.model.data.User;
import sample.com.organizerapp.ui.main.EventListActivity;
import sample.com.organizerapp.ui.main.MainActivity;
import sample.com.organizerapp.util.AppSettings;


public class EventActivity extends AppCompatActivity {

    private Realm realm;
    private User user;
    private ActivityEventMainBinding binding;
    private TextView txtName;
    private TextView txtEmail;
    private ImageView imgProfile;
    private AppSettings appSettings;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();


//        Bundle extras = getIntent().getExtras();
//        String username_string = extras.getString("EXTRA_USERNAME");
//


        appSettings = AppSettings.getAppSettingsFromSharedPreference(this);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Event Details");



        EventTabAdapter mAdapter = new EventTabAdapter(getSupportFragmentManager());

        binding.viewPager.setAdapter(mAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager, true);
       // binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

    }




    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(EventActivity.this)
                .setTitle("Are you sure you want to back to event list?")
                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EventActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();
    }






}
