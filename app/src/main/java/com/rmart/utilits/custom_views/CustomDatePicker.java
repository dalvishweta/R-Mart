package com.rmart.utilits.custom_views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Calendar;
import java.util.Locale;

public class CustomDatePicker implements DatePickerDialog.OnDateSetListener {
    private AppCompatTextView dateTV;
    private Calendar myCalendar;
    String ddmmyyy;
    public CustomDatePicker(AppCompatTextView tv, Context ctx, String date) {
        ddmmyyy = date;
        setDate( tv, ctx);
    }

    public void setDate(AppCompatTextView tv, Context ctx){
        this.dateTV = tv;
        myCalendar = Calendar.getInstance();
        new DatePickerDialog(ctx, this, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker,int year, int monthOfYear, int dayOfMonth) {
        // In which you need put here
        //String myFormat = DD_MM_YYYY;
        SimpleDateFormat simple = new SimpleDateFormat(ddmmyyy, Locale.getDefault());
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateTV.setText(simple.format(myCalendar.getTime()));
    }
}
