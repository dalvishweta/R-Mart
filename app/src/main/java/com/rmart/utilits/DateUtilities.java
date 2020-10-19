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
        Calendar calendar = Calendar.getInstance();
        Locale locale = Locale.getDefault();
        if (!TextUtils.isEmpty(dateValue)) {
            SimpleDateFormat formatter1 = new SimpleDateFormat(Utils.DD_MM_YYYY, locale);
            SimpleDateFormat formatter2 = new SimpleDateFormat(Utils.YYYY_MM_DD, locale);
            SimpleDateFormat[] formats = new SimpleDateFormat[]{formatter1, formatter2};
            Date parsedDate;
            for (SimpleDateFormat format : formats) {
                try {
                    parsedDate = format.parse(dateValue);
                    calendar.setTime(parsedDate);
                    calendar.setTime(parsedDate);
                    break;
                } catch (ParseException e) {
                    LoggerInfo.errorLog("getCalendarFromString exception", e.getMessage());
                }
            }
        }
        return calendar;
    }
}
