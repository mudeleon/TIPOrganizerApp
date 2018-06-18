package sample.com.organizerapp.ui.event.raffle;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.com.organizerapp.app.App;
import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Attendee;
import sample.com.organizerapp.model.data.Prize;
import sample.com.organizerapp.model.response.AttendeeListResponse;
import sample.com.organizerapp.model.response.BasicResponse;
import sample.com.organizerapp.model.response.MarkAttendeeResponse;


@SuppressWarnings("ConstantConditions")
public class RafflePresenter extends MvpBasePresenter<RaffleView> {

    private Realm realm;

    public void onStart() {
        realm = Realm.getDefaultInstance();
     //   user = App.getUser();
    }

    public void loadPrizeList(String eventID,String apiToken) {


                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                  //  realm.delete(Prize.class);

                                    List<Prize> prize = new ArrayList<>();
                                    Prize prize1 = new Prize();
                                    prize1.setId(1);
                                    prize1.setPriceName("Sample Prize");
                                    prize1.setPriceDescription("The Sample Prize and the quick brown fox jumps over the lazy dog");
                                    prize1.setPrizeQuantity("2");
                                    prize1.setClickStatus(false);
                                    prize.add(prize1);
                                    realm.copyToRealmOrUpdate(prize1);
                                    prize1.setId(2);
                                    prize1.setPriceName("Sample Prize2");
                                    prize1.setPriceDescription("The Sample Prize2");
                                    prize1.setPrizeQuantity("3");
                                    prize1.setClickStatus(false);
                                    prize.add(prize1);
                                    realm.copyToRealmOrUpdate(prize1);
                                    prize1.setId(3);
                                    prize1.setPriceName("Sample Prize3");
                                    prize1.setPriceDescription("The Sample Prize3");
                                    prize1.setPrizeQuantity("4");
                                    prize1.setClickStatus(false);
                                    prize.add(prize1);
                                    realm.copyToRealmOrUpdate(prize1);
//                                    Prize prize2 = new Prize();
//                                    prize2.setId(2);
//                                    prize2.setPriceName("Sample Prize2");
//                                    prize2.setPriceDescription("The Sample Prize2");
//                                    prize2.setPrizeQuantity("3");

                                    realm.copyToRealmOrUpdate(prize1);


                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().setPrizeList();
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    realm.close();
                                    error.printStackTrace();
                                    if (isViewAttached())
                                        getView().showError(error.getLocalizedMessage());

                                }
                            });

    }


    public void loadWinnersList(String eventID,String apiToken) {


        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //  realm.delete(Prize.class);

                List<Prize> prize = new ArrayList<>();
                Prize prize1 = new Prize();
                prize1.setId(1);
                prize1.setPriceName("Sample Prize");
                prize1.setPriceDescription("The Sample Prize and the quick brown fox jumps over the lazy dog");
                prize1.setPrizeQuantity("2");
                prize1.setClickStatus(false);
                prize.add(prize1);
                realm.copyToRealmOrUpdate(prize1);
                prize1.setId(2);
                prize1.setPriceName("Sample Prize2");
                prize1.setPriceDescription("The Sample Prize2");
                prize1.setPrizeQuantity("3");
                prize1.setClickStatus(false);
                prize.add(prize1);
                realm.copyToRealmOrUpdate(prize1);
                prize1.setId(3);
                prize1.setPriceName("Sample Prize3");
                prize1.setPriceDescription("The Sample Prize3");
                prize1.setPrizeQuantity("4");
                prize1.setClickStatus(false);
                prize.add(prize1);
                realm.copyToRealmOrUpdate(prize1);
//                                    Prize prize2 = new Prize();
//                                    prize2.setId(2);
//                                    prize2.setPriceName("Sample Prize2");
//                                    prize2.setPriceDescription("The Sample Prize2");
//                                    prize2.setPrizeQuantity("3");

                realm.copyToRealmOrUpdate(prize1);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                getView().setPrizeList();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
                error.printStackTrace();
                if (isViewAttached())
                    getView().showError(error.getLocalizedMessage());

            }
        });

    }


    public  void editPrize(String name,String description,String quantity,String apiToken){


    }


    public void attendeeShuffler(){

        String[] animals = {"cat sasa","dog qwqwqw","parrot rarar","fish tatatra"};
        for (int i = 0; i < animals.length; ++i) {
            List<Character> letters = new ArrayList<>(animals[i].length());
            for (char c : animals[i].toCharArray()) {
                letters.add(c);
            }
            Collections.shuffle(letters);
            StringBuilder builder = new StringBuilder();
            for (char c : letters) {
                builder.append(c);
            }
            animals[i] = builder.toString();
        }

        for (String s : animals) {
            Log.d(">>>>>",s);
        }
    }

    public void onStop() {
        realm.close();
    }
}
