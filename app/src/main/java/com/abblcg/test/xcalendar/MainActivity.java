package com.abblcg.test.xcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                showTv.setText(TimeUtil.printLogCalendar(xcv.getCalendar()));
            }
        });
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xcv.goToNextMonth();
                showTv.setText(TimeUtil.printLogCalendar(xcv.getCalendar()));
            }
        });
    }
}
