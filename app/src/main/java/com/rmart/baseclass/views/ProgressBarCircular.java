package com.rmart.baseclass.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.rmart.R;

public class ProgressBarCircular extends RelativeLayout {


    final static String ANDROID_XML = "http://schemas.android.com/apk/res/android";

    //int backgroundColor = Color.parseColor("#FF0000");
    private int backgroundColor;

    float radius1 = 0;
    float radius2 = 0;
    int cont = 0;
    boolean firstAnimationOver = false;

    int arcD = 1;
    int arcO = 0;
    float rotateAngle = 0;
    int limit = 0;
    private int transparentColor;

    public ProgressBarCircular(Context pContext) {
        super(pContext);
        backgroundColor = ContextCompat.getColor(pContext, R.color.colorPrimary);
        transparentColor = ContextCompat.getColor(pContext, android.R.color.transparent);
    }

    public ProgressBarCircular(Context pContext, AttributeSet attrs) {
        super(pContext, attrs);
        backgroundColor = ContextCompat.getColor(pContext, R.color.colorPrimary);
        setAttributes(pContext, attrs);
    }

    public int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    // Set attributes of XML to View
    @SuppressWarnings("deprecation")
    protected void setAttributes(Context pContext, AttributeSet attrs) {

        setMinimumHeight(dpToPx(32, getResources()));
        setMinimumWidth(dpToPx(32, getResources()));

        //Set background Color
        // Color by resource
        int backgroundColor = attrs.getAttributeResourceValue(ANDROID_XML, "background", -1);
        if (backgroundColor != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setBackgroundColor(ContextCompat.getColor(pContext, backgroundColor));
            } else {
                setBackgroundColor(getResources().getColor(backgroundColor));
            }
        } else {
            // Color by hexadecimal
            String background = attrs.getAttributeValue(ANDROID_XML, "background");
            if (background != null)
                setBackgroundColor(Color.parseColor(background));
            else
                setBackgroundColor(Color.RED);
        }
        setMinimumHeight(dpToPx(3, getResources()));
    }

    protected int makePressColor() {
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor) & 0xFF;
        return Color.argb(128, r, g, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!firstAnimationOver)
            drawFirstAnimation(canvas);
        if (cont > 0)
            drawSecondAnimation(canvas);
        invalidate();
    }

    private void drawFirstAnimation(Canvas canvas) {
        int newWidth = getWidth() / 2;
        int newHeight = getHeight() / 2;
        if (radius1 < newWidth) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            radius1 = (radius1 >= newWidth) ? (float) getWidth() / 2 : radius1 + 1;
            canvas.drawCircle(newWidth, newHeight, radius1, paint);
        } else {
            Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas temp = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            temp.drawCircle(newWidth, newHeight, newHeight, paint);
            Paint transparentPaint = new Paint();
            transparentPaint.setAntiAlias(true);
            transparentPaint.setColor(transparentColor);
            transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            if (cont >= 50) {
                radius2 = (radius2 >= newWidth) ? (float) getWidth() / 2 : radius2 + 1;
            } else {
                radius2 = (radius2 >= newWidth - dpToPx(4, getResources())) ? (float) getWidth() / 2 - dpToPx(4, getResources()) : radius2 + 1;
            }
            temp.drawCircle(newWidth, newHeight, radius2, transparentPaint);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
            if (radius2 >= newWidth - dpToPx(4, getResources()))
                cont++;
            if (radius2 >= newWidth)
                firstAnimationOver = true;
        }
    }

    private void drawSecondAnimation(Canvas canvas) {
        if (arcO == limit)
            arcD += 6;
        if (arcD >= 290 || arcO > limit) {
            arcO += 6;
            arcD -= 6;
        }
        if (arcO > limit + 290) {
            limit = arcO;
            arcO = limit;
            arcD = 1;
        }
        int newWidth = getWidth() / 2;
        int newHeight = getHeight() / 2;
        rotateAngle += 4;
        canvas.rotate(rotateAngle, newWidth, newHeight);

        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
        temp.drawArc(new RectF(0, 0, getWidth(), getHeight()), arcO, arcD, true, paint);
        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        temp.drawCircle(newWidth, newHeight, (newWidth) - dpToPx(4, getResources()), transparentPaint);

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }

    // Set color of background
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        this.backgroundColor = color;
    }
}
