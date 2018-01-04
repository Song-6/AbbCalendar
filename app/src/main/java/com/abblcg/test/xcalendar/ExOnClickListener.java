package com.abblcg.test.xcalendar;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Abb on 2018/1/4.
 */

public interface ExOnClickListener {
    void onClickInTheMonth(View v, Calendar calendar);

    void onClickInLastMonth(View v, Calendar calendar);

    void onClickInNextMonth(View v, Calendar calendar);
}
