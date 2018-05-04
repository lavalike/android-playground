package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.wangzhen.annotations.BindView;

/**
 * 编译时注解
 */
public class CompileAnnotationActivity extends BaseActivity {

    @BindView(R.id.tv_build)
    public TextView tvBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile_annotation);
//        ButterKnife.bind(this);
        tvBuild = (TextView) findViewById(R.id.tv_build);
//        tvBuild.setText("编译时注解动态生成GeneratedClass类，调用其方法getMessage(),结果为：" + new GeneratedClass().getMessage());
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_annotation_compile));
    }
}
