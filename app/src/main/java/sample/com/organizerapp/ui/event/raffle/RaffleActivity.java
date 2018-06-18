package sample.com.organizerapp.ui.event.raffle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.SupportMapFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import sample.com.organizerapp.R;
import sample.com.organizerapp.databinding.ActivityEventRaffleBinding;
import sample.com.organizerapp.databinding.DialogAttendeeProfileBinding;
import sample.com.organizerapp.databinding.DialogEditPrizeBinding;
import sample.com.organizerapp.databinding.DialogEditProfileBinding;
import sample.com.organizerapp.databinding.DialogRaffleBinding;
import sample.com.organizerapp.model.data.Attendee;
import sample.com.organizerapp.model.data.Prize;
import sample.com.organizerapp.model.data.Token;
import sample.com.organizerapp.model.data.User;
import sample.com.organizerapp.ui.event.EventActivity;
import sample.com.organizerapp.ui.main.MainActivity;
import sample.com.organizerapp.util.AppSettings;


import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class RaffleActivity
        extends MvpViewStateFragment<RaffleView, RafflePresenter>
        implements SwipeRefreshLayout.OnRefreshListener, RaffleView{

    private static final String TAG = RaffleActivity.class.getSimpleName();
    private ActivityEventRaffleBinding binding;
    private Realm realm;
    private User user;
    private Token token;
    private RealmResults<Prize> prizeRealmResults;
    private RealmResults<Attendee> winnerRealmResults;
    private String searchText;
    public String id;
    private String eventID;
    private AppSettings appSettings;
    private RafflePrizeAdapter prizeListAdapter;
    private RaffleWinnerAdapter winnerListAdapter;
    KonfettiView viewKonfetti;
    private final int PERMISSION_CODE = 9235;
    private static final int MY_PERMISSIONS_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private SupportMapFragment mapFragment;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<RaffleView> createViewState() {
        setRetainInstance(true);
        return new RaffleViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_event_raffle, container, false);
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

        viewKonfetti = getView().findViewById(R.id.viewKonfetti);

       prizeListAdapter = new RafflePrizeAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(prizeListAdapter);

        presenter.onStart();


        appSettings = AppSettings.getAppSettingsFromSharedPreference(getContext());
        eventID = appSettings.getEventDetailId();

        presenter.loadPrizeList(eventID,token.getToken());

        prizeListAdapter = new RafflePrizeAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(prizeListAdapter);


        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
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



        winnerListAdapter = new RaffleWinnerAdapter(getActivity(), getMvpView());
        binding.recyclerView2.setAdapter(winnerListAdapter);



        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView2.setItemAnimator(new DefaultItemAnimator());

        // binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


             if(prizeListAdapter.getChoosen() != -1 || prizeListAdapter.prizeQuantity() > 0)
             {
               dialogRaffle();
             }
             else
                 showError("Please Select Prize to Raffle");
                    presenter.attendeeShuffler();
            }
        });



        }
//    public static int getScreenWidth(Activity activity) {
//        Point size = new Point();
//        activity.getWindowManager().getDefaultDisplay().getSize(size);
//        return size.x;
//    }

    public void dialogRaffle()
    {

        final Dialog dialog = new Dialog(getContext(),R.style.RaffleDialogTheme);
        //LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final DialogRaffleBinding dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_raffle,
                null,
                false);
//        dialog.getWindow()
//                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialogBinding.setPrize(prizeListAdapter.getPrize());
        String imageURL = "";//Endpoints.IMAGE_URL.replace(Endpoints.IMG_HOLDER, promo.getImage());
        Glide.with(getActivity())
                .load(imageURL)
                .error(R.drawable.tip_banner)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(dialogBinding.prizeImage);


       

        dialogBinding.raffleStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startRaffle(dialogBinding);

            }
        });


        dialogBinding.raffleReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildKonfetti();
                dialogBinding.raffleWinner.setVisibility(View.VISIBLE);
                dialogBinding.raffleTag.setVisibility(View.VISIBLE);
                dialogBinding.raffleDraw.setVisibility(View.VISIBLE);
            }
        });

        dialogBinding.raffleTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewKonfetti.reset();
                dialog.dismiss();

            }
        });
        dialogBinding.raffleDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRaffle(dialogBinding);

            }
        });


//        dialogBinding.raffleName;
//        dialogBinding.rafflePrize;
//        dialogBinding.raffleStart;
//        dialogBinding.raffleTag;
//        dialogBinding.raffleTitle;
//        dialogBinding.raffleWinner;


        dialogBinding.raffleDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewKonfetti.reset();
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();
    }


    public void startRaffle(final DialogRaffleBinding dialogBinding)
    {


        dialogBinding.raffleTime.setVisibility(View.VISIBLE);
        dialogBinding.raffleName.setVisibility(View.VISIBLE);
        dialogBinding.raffleStart.setVisibility(View.GONE);


        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                dialogBinding.raffleTime.setText(String.valueOf((millisUntilFinished / 1000)-1));



            }

            public void onFinish() {
                dialogBinding.raffleTime.setVisibility(View.GONE);
                dialogBinding.raffleReveal.setVisibility(View.VISIBLE);
            }

        }.start();

    }

    public void buildKonfetti() {
       viewKonfetti.build()
                .addColors(Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 8f)
                .setFadeOutEnabled(false)
                .setTimeToLive(5000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, binding.viewKonfetti.getWidth() + 50f, -50f, -50f)
                .stream(30, -1);
    }

    public void loadData()
    {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        prizeRealmResults = realm.where(Prize.class).findAll();
        winnerRealmResults = realm.where(Attendee.class).findAll();

        if (prizeRealmResults.size()>0) {

            getMvpView().setPrizeList();

        }else
        {
            presenter.loadPrizeList(eventID,token.getToken());

        }


    }




    @NonNull
    @Override
    public RafflePresenter createPresenter() {
        return new RafflePresenter();
    }


    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }





    @Override
    public void onDestroy() {
        presenter.onStop();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {

            presenter.loadPrizeList(eventID,token.getToken());
    }
    @Override
    public void stopRefresh() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setPrizeList(){

        prizeRealmResults = realm.where(Prize.class).findAll();
       prizeListAdapter.setRaffleResult(realm.copyToRealmOrUpdate(prizeRealmResults.where()
               .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        prizeListAdapter.notifyDataSetChanged();


        winnerRealmResults = realm.where(Attendee.class).findAll();
        winnerListAdapter.setRaffleResultWinner(realm.copyToRealmOrUpdate(winnerRealmResults.where()
                .contains("status","2")
                .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        prizeListAdapter.notifyDataSetChanged();


        if(winnerListAdapter.getItemCount()==0)
        {
            binding.rafflelistNoRecyclerView.setVisibility(View.VISIBLE);
            binding.recyclerView2.setVisibility(View.GONE);
        }


    }





    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }





    @Override
    public void prizeDelete(Prize prize) {


        new AlertDialog.Builder(getContext())
                .setTitle("Are you sure you want to delete "+prize.getPriceName()+" ?")
                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();


    }

    @Override
    public void prizeEdit(Prize prize) {


       final Dialog dialog = new Dialog(getContext());
        //LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final DialogEditPrizeBinding dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_edit_prize,
                null,
                false);

        dialogBinding.setPrize(prize);
        String imageURL = "";//Endpoints.IMAGE_URL.replace(Endpoints.IMG_HOLDER, promo.getImage());
        Glide.with(getActivity())
                .load(imageURL)
                .error(R.drawable.tip_banner)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(dialogBinding.imageView);

        dialogBinding.editProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editPrize(dialogBinding.etName.getText().toString(),
                        dialogBinding.etDesc.getText().toString(),
                        dialogBinding.etQuantity.getText().toString(),
                        token.getToken());
                dialog.dismiss();
            }
        });

        dialogBinding.editProfileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();


    }

    @Override
    public void showPrizeDetails(Prize prize) {


        Dialog dialog = new Dialog(getContext());
        //LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DialogEditPrizeBinding dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_edit_prize,
                null,
                false);

        dialogBinding.setPrize(prize);
        String imageURL = "";//Endpoints.IMAGE_URL.replace(Endpoints.IMG_HOLDER, promo.getImage());
        Glide.with(getActivity())
                .load(imageURL)
                .error(R.drawable.tip_banner)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(dialogBinding.imageView);

        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();


    }






}
