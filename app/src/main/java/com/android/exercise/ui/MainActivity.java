package com.android.exercise.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.activity.GreendaoActivity;
import com.android.exercise.ui.activity.RealmActivity;
import com.android.exercise.ui.activity.RetrofitActivity;
import com.android.exercise.ui.adapter.FunctionAdapter;

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
        initRecycler();
        initList();
    }

    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        recyclerview.setLayoutManager(manager);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.app_name), false);
    }

    /**
     * 添加数据
     */
    private void initList() {
        mList = new ArrayList<>();
        mList.add(getString(R.string.item_realm));
        mList.add(getString(R.string.item_greendao));
        mList.add(getString(R.string.item_retrofit));

        mAdapter = new FunctionAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                redirect(position, (String) data);
            }
        });
        recyclerview.setAdapter(mAdapter);
    }

    private void redirect(int position, String data) {
        switch (data) {
            case "Realm":
                startActivity(new Intent(mContext, RealmActivity.class));
                break;
            case "GreenDao":
                startActivity(new Intent(mContext, GreendaoActivity.class));
                break;
            case "Retrofit 2.0":
                startActivity(new Intent(mContext, RetrofitActivity.class));
                break;
        }

    }
}
