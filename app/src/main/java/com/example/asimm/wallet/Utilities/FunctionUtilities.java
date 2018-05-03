package com.example.asimm.wallet.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Abdul Haseeb on 4/30/2018.
 */

public class FunctionUtilities {

    public enum dateEnum {
        DAY, MONTH, DAY_OF_WEEK, MONTHNO, YEAR
    }


    public static String getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDate(dateEnum d, Date date) {
        SimpleDateFormat dateFormat;
        switch (d) {
            case DAY:
                dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
                return dateFormat.format(date);
            case MONTH:
                dateFormat = new SimpleDateFormat("MMM", Locale.getDefault());
                return dateFormat.format(date);
            case DAY_OF_WEEK:
                dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                return dateFormat.format(date);
            case MONTHNO:
                dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
                return dateFormat.format(date);
            case YEAR:
                dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                return dateFormat.format(date);
            default:
                return null;
        }
    }

}
