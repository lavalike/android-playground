package com.android.exercise.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.wangzhen.annotations.BindView;
import com.wangzhen.butterknife_api.ButterKnife;

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
        ButterKnife.bind(this);
        tvBuild.setText("通过编译时注解完成控件实例化");
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_annotation_compile));
    }
}
