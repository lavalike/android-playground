package com.android.exercise.ui.activity.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityFlowLayoutBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * 流式布局
 * Created by wangzhen on 2016/11/24
 */
public class FlowLayoutActivity extends BaseActivity {
    private ActivityFlowLayoutBinding binding;
    private final String[] mVals = new String[]{"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome", "Button ImageView", "TextView", "Helloworld", "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityFlowLayoutBinding.inflate(getLayoutInflater())).getRoot());
        initData();
    }

    private void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_layout, binding.flowlayout, false);
            tv.setText(mVals[i]);
            binding.flowlayout.addView(tv);
        }
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_flowlayout));
    }
}
