package sample.com.organizerapp.ui.event.attendee;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.com.organizerapp.app.App;
import sample.com.organizerapp.app.Constants;
import sample.com.organizerapp.model.data.Attendee;
import sample.com.organizerapp.model.response.AttendeeListResponse;
import sample.com.organizerapp.model.response.MarkAttendeeResponse;


@SuppressWarnings("ConstantConditions")
public class AttendeePresenter extends MvpBasePresenter<AttendeeView> {

    private Realm realm;

    public void onStart() {
        realm = Realm.getDefaultInstance();
     //   user = App.getUser();
    }

    public void loadAttendeeList(String eventID,String apiToken) {

        App.getInstance().getApiInterface().getAttendeeList(eventID,Constants.BEARER+apiToken, Constants.APPJSON)
                .enqueue(new Callback<AttendeeListResponse>() {
                    @Override
                    public void onResponse(Call<AttendeeListResponse> call, final Response<AttendeeListResponse> response) {
                        if (isViewAttached()) {
                            getView().stopRefresh();
                        }
                        if (response.isSuccessful()) {
                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.delete(Attendee.class);
                                    realm.copyToRealmOrUpdate(response.body().getData());


//                                    List<Attendee> event = response.body().getData();
//                                    Attendee prize1 = new Attendee();
//                                    prize1.setFirstName("Sasas");
//                                    prize1.setAddress("sasasas");
//                                    prize1.setBirthday("2121");
//                                    prize1.setCpNumber("0909");
//                                    prize1.setEmailAddress("Sasas");
//                                    prize1.setStatus("1");
//                                   event.add(prize1);
//                                    realm.copyToRealmOrUpdate(response.body().getData());

                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().setAttendeeList();
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
                    public void onFailure(Call<AttendeeListResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopRefresh();
                            getView().showError(t.getLocalizedMessage());
                        }
                    }
                });
    }

//9787576741667

    Attendee getAttendee(String id){
        return realm.where(Attendee.class)
                .equalTo("ticket_id", id)
                .findFirst();
    }


    Long getPresent()
    {
        return realm.where(Attendee.class)
                .equalTo("status", "1")
                .count();
    }

    Long getTotal()
    {
        return realm.where(Attendee.class)
                .count();
    }

    Long getNotPresent()
    {
        return realm.where(Attendee.class)
                .equalTo("status", "0")
                .count();
    }


    public void markAttendee(String eventID,String attendeeID,String apiToken) {

        App.getInstance().getApiInterface().markAttendee(eventID,attendeeID, Constants.APPJSON,Constants.BEARER+apiToken)
                .enqueue(new Callback<MarkAttendeeResponse>() {
                    @Override
                    public void onResponse(Call<MarkAttendeeResponse> call, final Response<MarkAttendeeResponse> response) {
                        if (isViewAttached()) {
                            getView().stopRefresh();
                        }
                        if (response.isSuccessful()) {

                            getView().showReturn(response.body().getMessage());
                        } else {
                            if (isViewAttached())
                                getView().showError(response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MarkAttendeeResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopRefresh();
                            getView().showError(t.getLocalizedMessage());
                        }
                    }
                });
    }


    public void onStop() {
        realm.close();
    }
}
