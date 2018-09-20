package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.manager.NotificationHelper;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.NotificationBean;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_notification));
    }

    @OnClick({R.id.btn_send_normal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_normal:
                sendNormal();
                break;
        }
    }

    private void sendNormal() {
        NotificationHelper.get(this)
                .send(new NotificationBean.Builder()
                        .setTitle("看完这篇文章，你的Linux基础就差不多了")
                        .setSummary("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层")
                        .build());
    }
}
