package com.android.exercise.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.exercise.IMyAidl;
import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AIDLActivity.java
 * Created by wangzhen on 2017/4/9.
 */
public class AIDLActivity extends BaseActivity {

    private IMyAidl iMyAidl;
    private boolean isSuccessBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        bindAIDL();
    }

    private void bindAIDL() {
        Intent intent = new Intent();
        //新版本，必须显示启动绑定服务
        intent.setComponent(new ComponentName("com.android.exercise", "com.android.exercise.service.IRemoteService"));
        isSuccessBound = bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_aidl), true);
    }

    @OnClick({R.id.btn_calcutor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calcutor:
                if (iMyAidl == null) return;
                try {
                    Random random = new Random();
                    int num1 = random.nextInt(100);
                    int num2 = random.nextInt(100);
                    showToast(num1 + "+" + num2 + "=" + iMyAidl.addNumber(num1, num2));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, name + "连接成功");
            iMyAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidl = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isSuccessBound) {
            unbindService(connection);
        }
    }
}
