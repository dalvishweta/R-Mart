package com.rmart.baseclass;

/**
 * Created by Satya Seshu on 16/10/20.
 */
public interface DateTimeInterface {

    void onDateSet(int year, int month, int dayOfMonth);

    void onTimeSet(int hour, int minutes, String amOrPm);
}
