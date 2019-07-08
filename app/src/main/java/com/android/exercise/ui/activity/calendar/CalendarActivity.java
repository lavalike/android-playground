package com.android.exercise.ui.activity.calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarRightTextHolder;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CalendarActivity 日历控件
 * Created by wangzhen on 2019-07-08.
 */
public class CalendarActivity extends BaseActivity {

    @BindView(R.id.calendar_layout)
    CalendarLayout calendarLayout;
    @BindView(R.id.calendar_view)
    CalendarView calendarView;
    private ToolBarRightTextHolder mToolbarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                mToolbarHolder.setTitle(String.format("%1$d年%2$d月", year, month));
            }
        });
        mToolbarHolder.setTitle(String.format("%1$d年%2$d月", calendarView.getCurYear(), calendarView.getCurMonth()));
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        mToolbarHolder = new ToolBarRightTextHolder(this, toolbar, getString(R.string.item_calendar), "视图");
        mToolbarHolder.getRightMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarLayout.isExpand()) {
                    calendarLayout.shrink();
                } else {
                    calendarLayout.expand();
                }
            }
        });
    }
}
