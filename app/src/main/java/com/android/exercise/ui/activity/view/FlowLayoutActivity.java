package com.android.exercise.ui.activity.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.widget.FlowLayout;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 流式布局
 * Created by wangzhen on 2016/11/24
 */
public class FlowLayoutActivity extends BaseActivity {
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;
    private String[] mVals = new String[]{"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome", "Button ImageView", "TextView", "Helloworld", "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_layout, flowlayout, false);
            tv.setText(mVals[i]);
            flowlayout.addView(tv);
        }
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_flowlayout));
    }
}
