package com.rmart.utilits;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Satya Seshu on 16/10/20.
 */
public class DateUtilities {

    public static String getDateStringFromCalendar(Calendar calendar) {
        Locale locale = Locale.getDefault();
        String dateValue;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(Utils.DD_MM_YYYY, locale); // dd MMM yyyy
            dateValue = formatter.format(calendar.getTime());
        } catch (Exception ex) {
            LoggerInfo.errorLog("getDateStringFromCalendar Exception", ex.getMessage());
            dateValue = getDateStringFromCalendar(Calendar.getInstance(locale));
        }
        return dateValue;
    }
}
