package com.abblcg.test.xcalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Abb on 2017/12/28.
 */

public class DayView extends RelativeLayout {

    private static final String belongTheMonthColor = "#333333";
    private static final String notBelongTheMonthColor = "#999999";

    private SimpleDateFormat format = new SimpleDateFormat("dd");

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Calendar seftCalendar;
    private Calendar theMothCalendar;
    private Calendar theClickCalendar;
    private TextView dayTv;

    public Calendar getSeftCalendar() {
        return seftCalendar;
    }

    public void setTheClickCalendar(Calendar theClickCalendar) {
        this.theClickCalendar = theClickCalendar;
    }

    public void setCalendar(Calendar seftCalendar, Calendar theMothCalendar) {
        this.seftCalendar = seftCalendar;
        this.theMothCalendar = theMothCalendar;
        String dayNum = format.format(seftCalendar.getTime());
        setText(dayNum);
        setColor();
    }

    public DayView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        LayoutParams layoutParams = new LayoutParams(-1, -1);
        dayTv = new TextView(context);
        dayTv.setGravity(Gravity.CENTER);

        dayTv.setLayoutParams(layoutParams);
        addView(dayTv);
    }

    public void setText(String text) {
        dayTv.setText(text);
    }

    private void setColor() {
        if (seftCalendar.get(Calendar.MONTH) != theMothCalendar.get(Calendar.MONTH)) {
            dayTv.setTextColor(Color.parseColor(notBelongTheMonthColor));
        } else {
            dayTv.setTextColor(Color.parseColor(belongTheMonthColor));
        }
    }

    public void setDefaultColor() {


        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern("yyyy-MM-dd HH:mm:ss");

        if (seftCalendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            setBackgroundColor(Color.parseColor("#59a3fc"));
        } else {
            setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (theClickCalendar != null && seftCalendar.get(Calendar.DAY_OF_YEAR) == theClickCalendar.get(Calendar.DAY_OF_YEAR)) {
            setBackgroundColor(Color.parseColor("#fd7247"));
        }
    }

    public void setClickAction() {
        if (theClickCalendar != null) {
            setBackgroundColor(Color.parseColor("#fd7247"));
        }
    }

    public void setExOnClickListener(final ExOnClickListener exOnClickListener) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seftCalendar.get(Calendar.MONTH) == theMothCalendar.get(Calendar.MONTH)) {
                    exOnClickListener.onClickInTheMonth(DayView.this, seftCalendar);
                } else if (seftCalendar.getTimeInMillis() < theMothCalendar.getTimeInMillis()) {
                    exOnClickListener.onClickInLastMonth(DayView.this, seftCalendar);
                } else {
                    exOnClickListener.onClickInNextMonth(DayView.this, seftCalendar);
                }
            }
        });
    }
}
