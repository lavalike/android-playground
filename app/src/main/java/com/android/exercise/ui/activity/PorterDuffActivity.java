package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.widget.RevealView;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PorterDuffActivity extends BaseActivity {

    @BindView(R.id.revealView)
    RevealView revealView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.startRevealAnim();
            }
        }, 1000);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_porter_duff));
    }
}
