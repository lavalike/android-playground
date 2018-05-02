package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.annotation.InjectLayout;
import com.android.exercise.annotation.InjectView;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.ViewInjectUtils;
import com.wangzhen.annotation.CustomAnnotation;
import com.wangzhen.annotation.processor.generated.GeneratedClass;

/**
 * 自定义注解
 */
@CustomAnnotation
@InjectLayout(R.layout.activity_annotation)
public class AnnotationActivity extends BaseActivity {

    @InjectView(R.id.tv_reflect)
    TextView tvReflect;
    @InjectView(R.id.tv_build)
    TextView tv_build;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.bind(this);
        tvReflect.setText("当前布局通过运行时注解@InjectLayout渲染，并使用@InjectView实例化控件");
        tv_build.setText("编译时注解动态生成GeneratedClass类，调用其方法getMessage(),结果为：" + new GeneratedClass().getMessage());
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_annotation));
    }
}
