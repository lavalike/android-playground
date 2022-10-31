package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.annotation.InjectLayout;
import com.android.exercise.annotation.InjectView;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.util.ViewInjectUtils;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * 运行时注解
 */
@InjectLayout(R.layout.activity_annotation_runtime)
public class RuntimeAnnotationActivity extends BaseActivity {

    @InjectView(R.id.tv_reflect)
    TextView tvReflect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.bind(this);
        tvReflect.setText("当前布局通过运行时注解@InjectLayout渲染，并使用@InjectView实例化控件");
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_annotation_runtime));
    }
}
