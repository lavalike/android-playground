package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * BitOptActivity
 * Created by wangzhen on 2018/12/6.
 */
public class BitOptActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bit_opt);
        ButterKnife.bind(this);
        opt();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_bit_opt));
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
        String text = tvResult.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            text += "\n";
        }
        tvResult.setText(text + s);
    }
}
