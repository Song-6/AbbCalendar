package com.abblcg.test.xcalendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.Calendar;

/**
 * Created by Abb on 2017/12/28.
 */

public class XCalenderView extends LinearLayout {

    private int mWidth;
    private int mHeight;
    private int childHeight;
    private boolean isGetSize;

    private static final int showDayNum = 42;

    public XCalenderView(Context context) {
        this(context, null);
    }

    public XCalenderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCalenderView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.getViewTreeObserver().addOnGlobalLayoutListener(listener);

    }

    private ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (!isGetSize) {
                mWidth = XCalenderView.this.getWidth();
                mHeight = XCalenderView.this.getHeight();
                childHeight = mHeight / (showDayNum / 7);
                XCalenderView.this.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                isGetSize = true;
                init();
            }
        }
    };

    /**
     * 记录当前月的第一天
     */
    private Calendar theCalendar;
    /**
     * 记录当前表格的第一天
     */
    private Calendar startCalendar;
    /**
     * 记录点击的那一天
     */
    private Calendar theClickCalendar;

    public Calendar getCalendar() {
        return theCalendar;
    }

    private void init() {
        removeAllViews();

        theCalendar = Calendar.getInstance();
        theCalendar.set(Calendar.DAY_OF_MONTH, 1);

        startCalendar = offsetCalendar(theCalendar);

        initTable();

        for (int i = 0; i < showDayNum; i++) {

            final DayView dayView = (DayView) getChildAt(i);

            final int x = i;

            dayView.setExOnClickListener(new ExOnClickListener() {
                @Override
                public void onClickInTheMonth(View v, Calendar calendar) {
                    showTableItem(dayView);

                    if (onDateChangeListener != null) {
                        onDateChangeListener.getChooseDay(calendar);
                    }
                }

                @Override
                public void onClickInLastMonth(View v, Calendar calendar) {
                    showTableItem(dayView);
                    goToLastMonth();

                    if (onDateChangeListener != null) {
                        onDateChangeListener.getChooseDay(calendar);
                    }
                }

                @Override
                public void onClickInNextMonth(View v, Calendar calendar) {
                    showTableItem(dayView);
                    goToNextMonth();

                    if (onDateChangeListener != null) {
                        onDateChangeListener.getChooseDay(calendar);
                    }
                }
            });
        }

        if (onDateChangeListener != null) {
            onDateChangeListener.getChooseMonth(theCalendar);
        }
    }

    private void showTableItem(DayView dayView) {
        for (int j = 0; j < showDayNum; j++) {
            DayView dv = (DayView) getChildAt(j);
            dv.setTheClickCalendar(null);
            dv.setDefaultColor();
        }
        theClickCalendar = Calendar.getInstance();
        theClickCalendar.setTimeInMillis(dayView.getSeftCalendar().getTimeInMillis());
        dayView.setTheClickCalendar(theClickCalendar);
        dayView.setDefaultColor();
        dayView.setClickAction();
    }

    private void initTable() {
        for (int i = 0; i < showDayNum; i++) {
            final Calendar seftCalendar = Calendar.getInstance();
            seftCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
            seftCalendar.add(Calendar.DAY_OF_YEAR, i);


            final DayView dayView = new DayView(getContext());
            addView(dayView);
            dayView.setCalendar(seftCalendar, theCalendar);
            dayView.setTheClickCalendar(null);
            dayView.setDefaultColor();
        }
    }


    public void goToLastMonth() {

        Log.i("DayView", TimeUtil.printLogCalendar(theClickCalendar));

        theCalendar.add(Calendar.MONTH, -1);

        startCalendar = offsetCalendar(theCalendar);

        showTable();

        if (onDateChangeListener != null) {
            onDateChangeListener.getChooseMonth(theCalendar);
        }

    }

    public void goToNextMonth() {

        Log.i("DayView", TimeUtil.printLogCalendar(theClickCalendar));

        theCalendar.add(Calendar.MONTH, 1);

        startCalendar = offsetCalendar(theCalendar);

        showTable();

        if (onDateChangeListener != null) {
            onDateChangeListener.getChooseMonth(theCalendar);
        }
    }

    private void showTable() {
        for (int i = 0; i < showDayNum; i++) {

            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
            newCalendar.add(Calendar.DAY_OF_YEAR, i);


            DayView dayView = (DayView) getChildAt(i);
            dayView.setCalendar(newCalendar, theCalendar);
            dayView.setTheClickCalendar(theClickCalendar);
            dayView.setDefaultColor();
        }
    }

    private Calendar offsetCalendar(Calendar theCalendar) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(theCalendar.getTimeInMillis());
        int weekday = getWeekday(startCalendar);
        if (weekday > 0) {
            startCalendar.add(Calendar.DAY_OF_YEAR, -weekday);
        }
        return startCalendar;
    }

    private int getWeekday(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return 6;
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            default:
                return 0;
        }
    }

    //给42个控件 进行排列
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        final int parentLeft = 0;

        int childTop = 0;
        int childLeft = parentLeft;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = childHeight;

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width;

            //We should warp every so many children
            if (i % 7 == 6) {
                childLeft = parentLeft;
                childTop += height;
            }

        }
    }

    //测量布局文件 对布局进行测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (specHeightMode == MeasureSpec.UNSPECIFIED || specWidthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }

        //The spec width should be a correct multiple
        final int measureTileSize = specWidthSize / 7;

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);


            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measureTileSize, MeasureSpec.EXACTLY);

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);


        }

    }

    private OnDateChangeListener onDateChangeListener;

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

}
