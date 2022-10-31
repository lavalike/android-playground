package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.ui.widget.SlidingMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义侧滑菜单
 * Created by wangzhen on 2016/11/19
 */
public class SlidingMenuActivity extends BaseActivity {

    @BindView(R.id.slidingmenu)
    SlidingMenu slidingmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_toggleMenu)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggleMenu:
                slidingmenu.toggleMenu();
                break;
        }
    }
}
