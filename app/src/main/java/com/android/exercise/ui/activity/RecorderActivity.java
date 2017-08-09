package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.RecorderButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Recorder
 * Created by wangzhen on 2017/8/8.
 */
public class RecorderActivity extends BaseActivity {

    @BindView(R.id.btn_record)
    RecorderButton btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btnRecord.setRecorderListener(new RecorderButton.IRecorderListener() {
            @Override
            public void onFinish(String path) {
                Toast.makeText(mContext, "文件地址：" + path, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_recorder));
    }
}
