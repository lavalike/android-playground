package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.jni.myJNI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JNIActivity extends BaseActivity {

    @BindView(R.id.tv_jni)
    TextView tvJni;

    static {
        System.loadLibrary("HelloJNI");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_jni));
    }

    @OnClick(R.id.btn_call)
    public void onViewClicked() {
        tvJni.setText(myJNI.sayHello());
    }
}
