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

/**
 * 自定义注解
 */
@InjectLayout(R.layout.activity_annotation)
public class AnnotationActivity extends BaseActivity {

    @InjectView(R.id.tv_reflect)
    TextView tvReflect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.bind(this);
        tvReflect.setText("通过反射注解实例化并赋值");
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_annotation));
    }
}
