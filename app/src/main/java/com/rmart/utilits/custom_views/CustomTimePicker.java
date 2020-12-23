package com.rmart.utilits.custom_views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.utilits.LoggerInfo;

import java.util.Calendar;
import java.util.Locale;

public class CustomTimePicker {

    public CustomTimePicker(EditText tv, Context ctx) {
        setDate(tv, ctx);
    }

    public void setDate(EditText tvDateField, Context ctx) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ctx,
                (tp, sHour, sMinute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, sHour);
                    calendar.set(Calendar.MINUTE, sMinute);
                    String time = getTimeStringFromCalendar2(calendar);
                    tvDateField.setText(time);
                }, hour, minutes, true);
        timePickerDialog.show();
    }

    private String getTimeStringFromCalendar2(Calendar calendar) {
        Locale locale = Locale.getDefault();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", locale); //
            return formatter.format(calendar.getTime());
        } catch (Exception ex) {
            LoggerInfo.errorLog("getTimeStringFromCalendar2", ex.getMessage());
        }
        return getTimeStringFromCalendar2(Calendar.getInstance(locale));
    }
}
