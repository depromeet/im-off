package com.depromeet.tmj.im_off.shared;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.depromeet.tmj.im_off.R;

import androidx.core.content.res.ResourcesCompat;

public class RoundProgressBar extends View {

    private Paint paint;
    private int roundColor;
    private int roundProgressColor;
    private int roundBackgroundColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int keepRoundType;
    private String text;
    public static final int KEEP = 1;

    private int max;
    private int progress;
    private boolean textIsDisplayable;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        //Get a custom attribute and the default value
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.WHITE);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        keepRoundType = mTypedArray.getInt(R.styleable.RoundProgressBar_keepRoundType, 0);//Keep the outer ring is filled circle
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        text = mTypedArray.getString(R.styleable.RoundProgressBar_text);

        mTypedArray.recycle();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Painting the outermost ring
         */
        int centre = getWidth() / 2; //X coordinates acquisition center
        int radius = (int) (centre - roundWidth / 2); //The radius of the ring
        paint.setColor(roundColor); //Set the colors of the rings
        paint.setStyle(Paint.Style.STROKE); //A hollow
        paint.setStrokeWidth(roundWidth); //Set the ring width
        paint.setAntiAlias(true);  //Antialiasing
        canvas.drawCircle(centre, centre, radius, paint); //Draw a circle

        /**
         * In an arc, draw ring schedule
         */

        //Setup schedule is solid or hollow
        paint.setStrokeWidth(roundWidth); //Set the ring width
        paint.setColor(roundProgressColor);  //The progress of the color settings

        RectF oval = null;
        if (keepRoundType == KEEP) {//Retaining ring
            radius -= roundWidth;
        }

        oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //Used to define the shape and the size of the circular boundaries

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(roundBackgroundColor);
        canvas.drawArc(oval, 0, 360, true, paint);  //According to the progress of an arc

        paint.setColor(roundProgressColor);
        if (progress != 0) // TODO("시간에 따른 각도 계산 필요")
            canvas.drawArc(oval, -90, 180, true, paint);  //According to the progress of an arc

        /**
         * Painting progress percentage
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(ResourcesCompat.getFont(getContext(), R.font.jalnan)); //Set the font

        if (textIsDisplayable) {

            float textWidth = paint.measureText(text);   //Measure the font width, we need according to the font width in the middle ring
            canvas.drawText(text, centre - textWidth / 2, centre + textSize / 2, paint); //Draw the percentage
        }

    }

    public synchronized int getMax() {
        return max;
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getCricleBackgroundColor() {
        return roundBackgroundColor;
    }

    public void setCricleBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

}