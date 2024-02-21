package com.android.exercise.ui.activity.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ThemedMenuTextToolbar;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityCalendarBinding;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * CalendarActivity 日历控件
 * Created by wangzhen on 2019-07-08.
 */
@SuppressLint("DefaultLocale")
public class CalendarActivity extends BaseActivity {
    private ActivityCalendarBinding binding;
    private ThemedMenuTextToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityCalendarBinding.inflate(getLayoutInflater())).getRoot());
        initCalendar();
    }

    private void initCalendar() {
        binding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                toolbar.setTitle(String.format("%1$d年%2$02d月%3$02d日", calendar.getYear(), calendar.getMonth(), calendar.getDay()));
            }
        });
        toolbar.setTitle(String.format("%1$d年%2$02d月%3$02d日", binding.calendarView.getCurYear(), binding.calendarView.getCurMonth(), binding.calendarView.getCurDay()));
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        toolbar = ToolbarFactory.themedMenu(this, getString(R.string.item_calendar), "视图", () -> {
            if (binding.calendarLayout.isExpand()) {
                binding.calendarLayout.shrink();
            } else {
                binding.calendarLayout.expand();
            }
        });
        return toolbar;
    }
}
