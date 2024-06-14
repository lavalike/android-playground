package com.android.playground.ui.activity.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityMmkvBinding;
import com.tencent.mmkv.MMKV;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.Locale;
import java.util.Map;

/**
 * MMKVActivity
 * Created by wangzhen on 2020/6/8.
 */
public class MMKVActivity extends BaseActivity {

    private SharedPreferences sharedPreferences;
    private ActivityMmkvBinding binding;
    private static final int COUNT = 10000;
    private MMKV mmkv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMmkvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initSharedPreference();
        initMMKV();
    }

    private void initViews() {
        binding.btnMmkvInsert.setOnClickListener(v -> {
            insertMMKV();
        });

        binding.btnPreferencesInsert.setOnClickListener(v -> {
            insertSharedPreferences();
        });

        binding.btnQuery.setOnClickListener(v -> {
            query();
        });

        binding.btnQueryPerformance.setOnClickListener(v -> {
            String[] allKeys = mmkv.allKeys();
            if (allKeys != null && allKeys.length == COUNT) {
                queryMMKV();
            }
            Map<String, ?> preferencesAll = sharedPreferences.getAll();
            if (preferencesAll != null && preferencesAll.size() == COUNT) {
                queryPreference();
            }
        });

        binding.btnClear.setOnClickListener(v -> {
            clearAll();
        });
    }

    private void queryPreference() {
        for (int i = 0; i < COUNT; i++) {
            sharedPreferences.getInt(String.valueOf(i), -1);
        }
    }

    private void queryMMKV() {
        for (int i = 0; i < COUNT; i++) {
            mmkv.getInt(String.valueOf(i), -1);
        }
    }

    private void query() {
        int mmkvSize = mmkv.allKeys() == null ? 0 : mmkv.allKeys().length;
        int preferenceSize = sharedPreferences.getAll().size();
        Toast.makeText(mContext, String.format(Locale.getDefault(), "MMKV %d 条, SharedPreference %d 条", mmkvSize, preferenceSize), Toast.LENGTH_SHORT).show();
    }

    private void clearAll() {
        MMKV.defaultMMKV().clearAll();
        sharedPreferences.edit().clear().apply();
    }

    private void initSharedPreference() {
        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
    }

    private void initMMKV() {
        MMKV.initialize(this);
        mmkv = MMKV.defaultMMKV();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_mmkv));
    }

    private void insertMMKV() {
        for (int i = 0; i < COUNT; i++) {
            mmkv.putInt(String.valueOf(i), i);
        }
        mmkv.commit();
    }

    private void insertSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < COUNT; i++) {
            editor.putInt(String.valueOf(i), i);
        }
        editor.commit();
    }
}