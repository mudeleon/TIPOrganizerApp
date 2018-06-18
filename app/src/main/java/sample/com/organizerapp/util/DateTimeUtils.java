package sample.com.organizerapp.util;

import android.text.format.DateFormat;
import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author pocholomia
 * @since 13/09/2016
 */
public class DateTimeUtils {

    public static final String FULL_23_HR_DATE = "yyyy-MM-dd";
    public static final String DATE_ONLY = "MMM dd, yyyy";
    public static final String DATE_NUM_ONLY = "MM.dd.yyyy";



    public static String eventListTimestampMonDate(String date) {


        try {
        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Date newDate = spf.parse(date);

        spf= new SimpleDateFormat("MMM dd");
        date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = "error";
        }


        return date;
    }

    public static String eventListTimestampYear(String date) {



        try {


            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Date  newDate = spf.parse(date);

            spf= new SimpleDateFormat("yyyy");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = "error";
        }


        return date;
    }

    public static String eventDetail(String date1,String date2) {

            String returnDate="";


        try {


            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date  newDate = spf.parse(date1);
            Date  newDate2 = spf.parse(date2);
            spf= new SimpleDateFormat("MMM dd");

            date1 = spf.format(newDate);
            date2 = spf.format(newDate2);



            if(date1.equals(date2))
            {
                SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd", Locale.US);
                date1 = formatter.format(newDate);
                returnDate = ""+date1;

                 formatter = new SimpleDateFormat("hh:mm a", Locale.US);
                 date1 = formatter.format(newDate);
                 date2 = formatter.format(newDate2);
                 returnDate = returnDate + "\n" + date1 + " - " +date2;



            }else
            {
                SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd", Locale.US);
                SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm a", Locale.US);
                date1 = formatter.format(newDate);
                returnDate = ""+date1;
                date1 = formatter2.format(newDate);
                returnDate += " "+date1;
               // Log.d(">>>>ret",returnDate);

                date2 = formatter.format(newDate2);
                returnDate = "\n"+date2;
                date2 = formatter2.format(newDate2);
                returnDate += " "+date2;

            }



        } catch (ParseException e) {
            e.printStackTrace();
            date1 = "error";
        }


        return returnDate;
    }

}
