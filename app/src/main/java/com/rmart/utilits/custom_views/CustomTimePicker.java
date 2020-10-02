package com.rmart.utilits.custom_views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Calendar;
import java.util.Locale;

public class CustomTimePicker {
    private AppCompatTextView dateTV;
    private Calendar myCalendar;
    public CustomTimePicker(AppCompatTextView tv, Context ctx) {
        setDate( tv, ctx);
    }

    public void setDate(AppCompatTextView tv, Context ctx){
        this.dateTV = tv;
        myCalendar = Calendar.getInstance();
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = myCalendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog =  new TimePickerDialog(ctx,
                (tp, sHour, sMinute) -> tv.setText(sHour + ":" + sMinute), hour, minutes, true);
        timePickerDialog.show();
    }
}
