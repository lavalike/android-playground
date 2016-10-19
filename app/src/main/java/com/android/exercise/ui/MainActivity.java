package com.android.exercise.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.adapter.FunctionAdapter;
import com.android.exercise.common.toolbar.CommonToolbar;
import com.android.exercise.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private FunctionAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new CommonToolbar(this, toolbar, "练习");
    }

    /**
     * 添加数据
     */
    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            mList.add("动画");

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        recyclerview.setLayoutManager(manager);

        mAdapter = new FunctionAdapter(mContext, mList);
        recyclerview.setAdapter(mAdapter);
    }
}
