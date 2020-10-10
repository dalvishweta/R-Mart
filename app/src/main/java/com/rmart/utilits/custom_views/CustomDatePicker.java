package com.rmart.utilits.custom_views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.R;
import com.rmart.utilits.Utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomDatePicker implements DatePickerDialog.OnDateSetListener {
    private AppCompatTextView dateTV;
    private Calendar myCalendar;
    private Context ctx;
    String ddmmyyy;
    public CustomDatePicker(AppCompatTextView tv, Context ctx, String date) {
        ddmmyyy = date;
        this.ctx = ctx;
        setDate( tv, ctx);
    }

    public void setDate(AppCompatTextView tv, Context ctx){
        this.dateTV = tv;
        String text = dateTV.getText().toString();
        myCalendar = Calendar.getInstance();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(Utils.YYYY_MM_DD); // here set the pattern as you date in string was containing like date/month/year
            Date d = sdf.parse(dateTV.getText().toString());
            myCalendar.setTime(d);

        }catch(ParseException ex){
            // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
        }
        new DatePickerDialog(ctx, this, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker,int year, int monthOfYear, int dayOfMonth) {
        // In which you need put here
        //String myFormat = DD_MM_YYYY;
        SimpleDateFormat simple = new SimpleDateFormat(ddmmyyy, Locale.getDefault());

        long previousTime = Calendar.getInstance().getTimeInMillis();

        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        myCalendar.set(Calendar.HOUR_OF_DAY, 23);
        myCalendar.set(Calendar.MINUTE, 59);
        long presentTime = myCalendar.getTimeInMillis();
        if (previousTime < presentTime) {
            dateTV.setText(simple.format(myCalendar.getTime()));
        } else {
            Toast.makeText(this.ctx, ctx.getText(R.string.erroe_expiry_date), Toast.LENGTH_SHORT).show();
        }
    }
}
