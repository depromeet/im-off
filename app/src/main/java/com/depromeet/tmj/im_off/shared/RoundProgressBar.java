package com.depromeet.tmj.im_off.shared;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.utils.DateUtils;
import com.depromeet.tmj.im_off.utils.DisplayUtils;

import java.util.Date;

import androidx.core.content.ContextCompat;
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
    private String textAMPM;
    private float startAngle;
    private float sweepAngle;
    public static final int KEEP = 1;
    private boolean textIsDisplayable;
    private boolean arcIsDisplayable = true;
    private boolean isResult = false;
    private Typeface font;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
        startAngle = 180f;
        sweepAngle = 0f;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        //Get a custom attribute and the default value
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.WHITE);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        keepRoundType = mTypedArray.getInt(R.styleable.RoundProgressBar_keepRoundType, 0);//Keep the outer ring is filled circle
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        text = mTypedArray.getString(R.styleable.RoundProgressBar_text);
        roundBackgroundColor = ContextCompat.getColor(getContext(), R.color.white);

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

        /**
         * In an arc, draw ring schedule
         */

        //Setup schedule is solid or hollow
        paint.setStrokeWidth(roundWidth); //Set the ring width
        paint.setColor(roundProgressColor);  //The progress of the color settings

        RectF oval;
        if (keepRoundType == KEEP) {//Retaining ring
            radius -= roundWidth;
        }

        int offset = DisplayUtils.dpToPixel(getContext(), 1);
        oval = new RectF(centre - radius - offset, centre - radius - offset,
                centre + radius + offset, centre + radius + offset);  //Used to define the shape and the size of the circular boundaries

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(roundBackgroundColor);
        canvas.drawArc(oval, startAngle, sweepAngle, true, paint);  //According to the progress of an arc

        if (arcIsDisplayable) {
            int innerDiff = DisplayUtils.dpToPixel(getContext(), 14);
            RectF innerOval = new RectF(centre - radius + innerDiff, centre - radius + innerDiff,
                    centre + radius - innerDiff, centre + radius - innerDiff);  //Used to define the shape and the size of the circular boundaries

            paint.setColor(roundProgressColor);
            canvas.drawArc(innerOval, startAngle, sweepAngle, true, paint);  //According to the progress of an arc
        }
        /**
         * Painting progress percentage
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        if(font == null) {
            font = ResourcesCompat.getFont(getContext(), R.font.jalnan);
        }
        paint.setTypeface(font); //Set the font

        if (textIsDisplayable) {
            float textWidth = paint.measureText(text);   //Measure the font width, we need according to the font width in the middle ring
            canvas.drawText(text, centre - textWidth / 2, centre + textSize / 2, paint); //Draw the percentage
            if (isResult && textAMPM != null) {
                paint.setTextSize(textSize / 2);
                canvas.drawText(textAMPM, centre - textWidth / 2, centre - textSize / 2, paint);
            }
        }

    }

    public void setTime(Date start, Date current) {
        startAngle = DateUtils.time2Angle(start); // 일 시작 시간
        sweepAngle = DateUtils.time2SweepAngle(start, current); // 일 한 시간
        invalidate();
    }

    public void setTimeWithAnim(Date start, Date current) {
        startAngle = DateUtils.time2Angle(start); // 일 시작 시간
        sweepAngle = DateUtils.time2SweepAngle(start, current); // 일 한 시간

        ArcAngleAnimation animation = new ArcAngleAnimation(this, sweepAngle);

        animation.setDuration(2000);
        startAnimation(animation);
        requestLayout();
    }

    public void setWorkingTimeWithAnim(int workingTime) {
        startAngle = 270;
        sweepAngle = (float) workingTime / 52 * 360;

        ArcAngleAnimation animation = new ArcAngleAnimation(this, sweepAngle);

        animation.setDuration(2000);
        startAnimation(animation);
        requestLayout();
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public void setCricleBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void setTextAMPM(String textAMPM) {
        this.textAMPM = textAMPM;
        isResult = true;
    }

    public void setTextIsDisplayable(boolean textIsDisplayable) {
        this.textIsDisplayable = textIsDisplayable;
    }

    public void setArcIsDisplayable(boolean arcIsDisplayable) {
        this.arcIsDisplayable = arcIsDisplayable;
    }

    public void setFont(Typeface font) {
        this.font = font;
    }

    private class ArcAngleAnimation extends Animation {
        private RoundProgressBar roundProgressBar;
        private float sweepAngle;

        public ArcAngleAnimation(RoundProgressBar roundProgressBar, float sweepAngle) {
            this.sweepAngle = sweepAngle;
            this.roundProgressBar = roundProgressBar;
            setInterpolator(new DecelerateInterpolator());
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            float angle = sweepAngle * interpolatedTime;

            roundProgressBar.setSweepAngle(angle);
            roundProgressBar.invalidate();
        }
    }
}