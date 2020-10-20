package com.rmart.utilits;

import android.icu.text.SimpleDateFormat;
import android.text.TextUtils;

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

    public static Calendar getCalendarFromString(String dateValue) {
        Date date;
        Locale locale = Locale.getDefault();
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(dateValue)) {
            return calendar;
        }
        SimpleDateFormat formatter1 = new SimpleDateFormat(Utils.MM_DD_YYYY, locale);
        SimpleDateFormat formatter2 = new SimpleDateFormat(Utils.YYYY_MM_DD, locale);
        //formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault());
        SimpleDateFormat[] formats = new SimpleDateFormat[]{formatter1, formatter2};
        for (SimpleDateFormat format : formats) {
            try {
                date = format.parse(dateValue);
                calendar.setTime(date);
            } catch (ParseException e) {
                LoggerInfo.errorLog("", e.getMessage());
            }
        }
        return calendar;
    }
}
