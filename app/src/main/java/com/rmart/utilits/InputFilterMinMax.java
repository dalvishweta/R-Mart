package com.rmart.utilits;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private Float min, max;
    private int maxfraction;

    public InputFilterMinMax(Float min, Float max,int maxfraction) {
        this.min = min;
        this.max = max;
        this.maxfraction = maxfraction;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Float.parseFloat(min);
        this.max = Float.parseFloat(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            Float input = Float.parseFloat(dest.toString() + source.toString());
            String inputValue = (dest.toString() + source.toString());
            if (isInRange(min, max, input, inputValue,maxfraction)) return null;
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(Float min, Float max, Float input, String inputValue,int maxfraction) {
        if (inputValue.contains(".") && (inputValue.split("\\.").length > 1)) {
            return (max > min ? input >= min && input <= max : input >= max && input <= min) && (inputValue.split("\\.")[1].length() < maxfraction);
        } else {
            return (max > min ? input >= min && input <= max : input >= max && input <= min);
        }
    }
}
