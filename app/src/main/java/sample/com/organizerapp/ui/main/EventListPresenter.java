package sample.com.organizerapp.ui.main;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.com.organizerapp.app.App;
import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.model.response.EventListResponse;


@SuppressWarnings("ConstantConditions")
public class EventListPresenter extends MvpBasePresenter<EventListView> {

    public void loadEventList(String apiToken) {

        App.getInstance().getApiInterface().getEventList(Constants.BEARER+apiToken, Constants.APPJSON)
                .enqueue(new Callback<EventListResponse>() {
                    @Override
                    public void onResponse(Call<EventListResponse> call, final Response<EventListResponse> response) {
                        if (isViewAttached()) {
                            getView().stopRefresh();
                        }
                        if (response.isSuccessful()) {
                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.delete(Event.class);
                                    realm.copyToRealmOrUpdate(response.body().getData());


                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().setEventList();
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
                        } else {
                            if (isViewAttached())
                                getView().showError(response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<EventListResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopRefresh();
                            getView().showError(t.getLocalizedMessage());
                        }
                    }
                });
    }




}
