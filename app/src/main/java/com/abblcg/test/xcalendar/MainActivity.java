package com.abblcg.test.xcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Abb on 2017/12/28.
 */

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView showTv = findViewById(R.id.showTv);
        final XCalenderView xcv = findViewById(R.id.xcv);
        Button lastBt = findViewById(R.id.lastBt);
        Button nextBt = findViewById(R.id.nextBt);
        showTv.setText(TimeUtil.printLogCalendar(null));
        lastBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xcv.goToLastMonth();
            }
        });
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xcv.goToNextMonth();
            }
        });

        xcv.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void getChooseMonth(Calendar calendar) {
                SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
                df.applyPattern("yyyy-MM-dd");
                String time = df.format(calendar.getTimeInMillis());
                showTv.setText(time);
            }

            @Override
            public void getChooseDay(Calendar calendar) {
                SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
                df.applyPattern("yyyy-MM-dd");
                String time = df.format(calendar.getTimeInMillis());
                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
