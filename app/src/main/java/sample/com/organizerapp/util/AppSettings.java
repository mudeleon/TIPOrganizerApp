package sample.com.organizerapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {

    public static final String TAG = AppSettings.class.getSimpleName();

    public static final String KEY_EVENT_DETAIL_ID ="event_detail_id";
    public static final String KEY_EVENT_DETAIL_NAME ="event_detail_name";



    private String eventDetailId;



    private String eventDetailName;

    public static AppSettings getAppSettingsFromSharedPreference(Context context) {
        AppSettings appSettings = new AppSettings();

        SharedPreferences settings = context.getSharedPreferences(TAG, Context.MODE_MULTI_PROCESS);


        appSettings.eventDetailId = settings.getString(KEY_EVENT_DETAIL_ID, "");
        appSettings.eventDetailName = settings.getString(KEY_EVENT_DETAIL_NAME, "");


        return appSettings;
    }



    public void save(Context context) {

        SharedPreferences settings = context.getSharedPreferences(TAG, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(KEY_EVENT_DETAIL_ID, eventDetailId);
        editor.putString(KEY_EVENT_DETAIL_NAME, eventDetailName);


        editor.commit();
        //editor.apply();
    }



    public String getEventDetailId() {
        return eventDetailId;
    }

    public void setEventDetailId(String eventDetailId) {
        this.eventDetailId = eventDetailId;
    }

    public String getEventDetailName() {
        return eventDetailName;
    }

    public void setEventDetailName(String eventDetailName) {
        this.eventDetailName = eventDetailName;
    }


}
