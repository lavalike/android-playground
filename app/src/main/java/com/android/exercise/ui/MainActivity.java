package com.android.exercise.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.common.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.adapter.FunctionAdapter;
import com.android.exercise.ui.adapter.base.BaseRecyclerAdapter;
import com.android.exercise.ui.base.BaseActivity;
import com.android.exercise.util.T;

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
        new ToolBarCommonHolder(this, toolbar, getString(R.string.app_name), false);
    }

    /**
     * 添加数据
     */
    private void initData() {
        mList = new ArrayList<>();
        mList.add("流式布局");

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        recyclerview.setLayoutManager(manager);

        mAdapter = new FunctionAdapter<>(mContext, mList);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                T.get(mContext).toast(position + ":" + data.toString());
            }
        });
        recyclerview.setAdapter(mAdapter);
    }
}