package sample.com.organizerapp.app;


public class Endpoints {



    //public static final String BASE_URL = "http://10.3.32.201/runrio";
    public static final String BASE_URL = "https://eventsapp.dgts.ph";


    static final String API_URL = BASE_URL + "/api/v1/organizer/";

    public static final String ID = "{id}";


    //Credentials
    public static final String LOGIN = "login";
    public static final String REGISTER = "user";
    public static final String CHANGE_PASSWORD = "user/changepass";
    public static final String UPDATE_ACCOUNT = "user/updateacct";
    public static final String FORGOT_PASSWORD = "user/forgotpass/{email_address}";
    public static final String LOGOUT = "logout";



    //Profile
    public static final String PROFILE = "update-profile";




    //Attendee
    public static final String ATTENDEE_MARK = "event/{event_id}/attendees/{attendee_id}/mark-as-attended-or-unattended";
    public static final String ATTENDEE_LIST = "event/{event_id}/attendees";

    //Events
    public static final String EVENT_URL_IMAGE = BASE_URL+"/";
    public static final String EVENT_LIST = "event";
    public static final String EVENT_DETAIL = "event" + "/" + ID;




     //Profile
     public static final String RUNNER_LIST = "participant_profile";
     public static final String RUNNER_DETAIL = RUNNER_LIST + "/" + ID;
     public static final String RUNNER_DELETE = RUNNER_LIST + "/change_status/" + ID;


     //transaction
     public static final String TRANSACTIONS = "reservations";
     public static final String TRANSACTIONS_DETAIL = TRANSACTIONS + "/" + ID;




}
