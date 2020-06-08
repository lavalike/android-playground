package com.android.exercise.ui.activity.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.tencent.mmkv.MMKV;

public class MMKVActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv);
        MMKV.initialize(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_mmkv));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_mmkv:
                mmkv();
                break;
            case R.id.btn_shared_preferences:
                sharedPreferences();
                break;
        }
    }

    private void mmkv() {
        long start = System.currentTimeMillis();
        MMKV mmkv = MMKV.defaultMMKV();
        for (int i = 0; i < 10000; i++) {
            mmkv.putInt(String.valueOf(i), i);
        }
        mmkv.apply();
        Toast.makeText(mContext, "MMKV耗时:" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
    }

    private void sharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            editor.putInt(String.valueOf(i), i);
        }
        editor.apply();
        Toast.makeText(mContext, "SharedPreferences耗时:" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
    }
}