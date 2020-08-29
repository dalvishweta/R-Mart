package com.rmart.utilits;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isValidMobile(String phone) {
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        Matcher m = p.matcher(phone);

        if (m.matches() && (phone != null && !phone.isEmpty()) && phone.length() == 10
                &&
                (Integer.parseInt(String.valueOf(phone.charAt(0))) > 5 && Integer.parseInt(String.valueOf(phone.charAt(0))) < 10)) {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }

    public static boolean internetConnectionAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(3000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (Exception e) {
            Log.d("Exception", "Exception: " + e.getMessage());
        }
        return inetAddress!=null && !inetAddress.equals("");
    }

    public static boolean isValidWord(String word) {
        if (word != null && word.length()>0) {
            return word.matches("^[a-zA-Z_]*$");
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getDateString(Long value) {
        return "";
    }

    public static int getIntegerValueFromString(String value) {
        int newValue = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                newValue = Integer.parseInt(value);
            } catch (Exception e) {

            }
        }
        return newValue;
    }

    public static double getDoubleValueFromString(String value) {
        double newValue = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                newValue = Double.parseDouble(value);
            } catch (Exception e) {

            }
        }
        return newValue;
    }

    public static Bitmap getRoundedBitmap(Bitmap bmp) {
        Bitmap sbmp;
        if(bmp == null) return bmp;
        int radius = bmp.getWidth();
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (bmp.getWidth() / factor),
                    (int) (bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }
}
