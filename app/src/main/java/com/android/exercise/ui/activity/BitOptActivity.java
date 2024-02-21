package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityBitOptBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * BitOptActivity
 * Created by wangzhen on 2018/12/6.
 */
public class BitOptActivity extends BaseActivity {
    private ActivityBitOptBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBitOptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        opt();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_bit_opt));
    }

    private void opt() {
        int a = 2;
        int b = 3;
        int result = a | b;
        setText("a | b  存储操作");
        setText("a & b  查询操作");
        setText("a & ~b 删除操作\n");

        setText("result = a | b --> " + a + " | " + b + " = " + result);
        setText("result & a --> " + ((result & a) == a));
        setText("result & a = " + (result & a));
        setText("result & b --> " + ((result & b) == b));
        setText("result & b = " + (result & b));

        setText("result &= ~a");
        result &= ~a;
        setText("result & a --> " + ((result & a) == a));
        setText("result & a = " + (result & a));

        setText("result &= ~b");
        result &= ~b;
        setText("result & b --> " + ((result & b) == b));
        setText("result & b = " + (result & b));

        setText("result |= a");
        result |= a;
        setText("result & a --> " + ((result & a) == a));
        setText("result & a = " + (result & a));
    }

    private void setText(String s) {
        String text = binding.tvResult.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            text += "\n";
        }
        binding.tvResult.setText(text + s);
    }
}
