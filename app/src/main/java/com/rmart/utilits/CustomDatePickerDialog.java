package com.rmart.utilits;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rmart.baseclass.Constants;
import com.rmart.baseclass.DateTimeInterface;

import java.util.Calendar;

/**
 * Created by Satya Seshu on 16/10/20.
 */
public class CustomDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int calendarMinMaxType = -1;
    private DateTimeInterface dateTimeInterface;

    public static CustomDatePickerDialog getInstance(int calendarMinMaxType, int year, int month, int dayOfMonth) {
        CustomDatePickerDialog customDatePickerDialog = new CustomDatePickerDialog();
        Bundle extras = new Bundle();
        extras.putInt(Constants.KEY_CALENDAR_MIN_MAX_TYPE, calendarMinMaxType);
        extras.putInt(Constants.KEY_YEAR, year);
        extras.putInt(Constants.KEY_MONTH, month);
        extras.putInt(Constants.KEY_DAY_OF_MONTH, dayOfMonth);
        customDatePickerDialog.setArguments(extras);
        return customDatePickerDialog;
    }

    public void setCallBack(DateTimeInterface dateTimeInterface) {
        this.dateTimeInterface = dateTimeInterface;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        Bundle extras = getArguments();
        if (extras != null) {
            calendarMinMaxType = extras.getInt(Constants.KEY_CALENDAR_MIN_MAX_TYPE);
            year = extras.getInt(Constants.KEY_YEAR, calendar.get(Calendar.YEAR));
            month = extras.getInt(Constants.KEY_MONTH, calendar.get(Calendar.MONTH));
            dayOfMonth =
                    extras.getInt(Constants.KEY_DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
        DatePickerDialog picker = new DatePickerDialog(requireActivity(), this, year, month, dayOfMonth);
        if(calendarMinMaxType == Constants.CALENDAR_MIN_TYPE) {
            Calendar minCalendar = Calendar.getInstance();
            picker.getDatePicker().setMinDate(minCalendar.getTime().getTime());
        } else if(calendarMinMaxType == Constants.CALENDAR_MAX_TYPE) {
            Calendar maxCalendar = Calendar.getInstance();
            picker.getDatePicker().setMaxDate(maxCalendar.getTime().getTime());
        }
        return picker;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        dateTimeInterface.onDateSet(year, month, dayOfMonth);
    }
}
