package com.rmart.utilits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String SUCCESS = "Success";

    public static final String RETAILER_ID = "4";
    public static final String CUSTOMER_ID = "5";
    public static final String DELIVERY_ID = "6";

    public static final String RETAILER = "retailer";
    public static final String CUSTOMER = "customer";
    public static final String DELIVERY = "delivery";

    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String MM_DD_YYYY = "MM-dd-yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String OPEN_ORDER_STATUS = "INI";
    public static final String CANCEL_BY_RETAILER = "CN";
    public static final String ACCEPTED_ORDER_STATUS = "CF";
    public static final String PACKED_ORDER_STATUS = "PKD";
    public static final String SHIPPED_ORDER_STATUS = "SPD";
    public static final String DELIVERED_ORDER_STATUS = "DL";
    public static final String CANCEL_BY_CUSTOMER = "REJ";


    public static final String CLIENT_ID = "2";
    public static final String PRODUCT = "View by product";
    public static final String BRAND = "View by brand";
    public static final String CATEGORY = "View by category";
    public static final String SUB_CATEGORY = "View by sub category";
    public static final String SUB_CATEGORY_PRODUCT = "Sub category products";
    public static final String BRAND_PRODUCTS = "BRAND PRODUCTS";
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final String SELECT_YOUR_GENDER = "Select your gender";

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public static boolean isValidMobile(String phone) {
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        Matcher m = p.matcher(phone);

        int i = Integer.parseInt(String.valueOf(phone.charAt(0)));
        int i1 = Integer.parseInt(String.valueOf(phone.charAt(0)));
        if (m.matches() && phone.length() == 10 && Integer.parseInt(String.valueOf(phone.charAt(0))) > 5 && Integer.parseInt(String.valueOf(phone.charAt(0))) < 10) {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }

    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        if(capabilities != null) {
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR);
        }
        return false;
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

    public static String getDateString(String value, String format, String requiredFormat) {
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(value);
            String convertedDate = new SimpleDateFormat(requiredFormat).format(date);
            return convertedDate;
        } catch (Exception e) {
            return value;
        }
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

    public static void openDialPad(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    public static void openGmailWindow(Context context, String emailId) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + emailId));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(emailIntent);
    }

    public static String roundOffDoubleValue(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    public static void enableViews(View pView) {
        pView.setAlpha(1.0f);
        pView.setEnabled(true);
    }

    public static void disableViews(View pView) {
        pView.setAlpha(0.5f);
        pView.setEnabled(false);
    }

    public static String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        if (!TextUtils.isEmpty(videoUrl)) {
            return "https://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
        }
        return null;
    }

    public static String getYoutubeVideoIdFromUrl(String inUrl) {
        inUrl = inUrl.replace("&feature=youtu.be", "");
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed/)[^#&?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static String  isValidPassword(String password) {
        //Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher;
        StringBuilder stringBuffer = new StringBuilder();
        // matcher = pattern.matcher(password);

        if(!Pattern.compile(".*[0-9].*").matcher(password).matches()) {
            stringBuffer.append("* Must contains one digit from 0-9.").append("\n");
        }
        if(!Pattern.compile(".*[a-z].*").matcher(password).matches()) {
            stringBuffer.append("* Must contains one lowercase characters.").append("\n");
        }
        if (!Pattern.compile(".*[A-Z].*").matcher(password).matches()) {
            stringBuffer.append("* Must contains one uppercase characters.").append("\n");
        }
        if (!Pattern.compile(".*[@#$%].*").matcher(password).matches()) {
            stringBuffer.append("* Must contains one special symbols in the list \"@#$%\".").append("\n");
        }
        if (password.length() < 8 || password.length() > 11) {
            stringBuffer.append("* Length at least 8 characters and maximum of 11.").append("\n");
        }
        return stringBuffer.toString();
    }

    public static boolean isValidPinCode(String pinCode) {
        String regex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
        Pattern p = Pattern.compile(regex);
        if (pinCode == null) {
            return false;
        }
        Matcher m = p.matcher(pinCode);
        return m.matches();
    }

    public static boolean isValidYoutubeUrl(String youTubeURl) {
        String pattern = "^(http(s)?://)?((w){3}.)?youtu(be|.be)?(\\.com)?/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
            return true;
        }
        return false;
    }

    public static Bitmap getCompressBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, out);
        return bitmap;
    }
    public static boolean isValidPanCardNo(String panCardNo) {
        // Regex to check valid PAN Card number.
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        // Compile the ReGex
        Pattern p = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        // If the PAN Card number
        // is empty return false
        if (panCardNo == null) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given
        // PAN Card number using regular expression.
        Matcher m = p.matcher(panCardNo);
        // Return if the PAN Card number
        // matched the ReGex
        return m.matches();
    }
}
