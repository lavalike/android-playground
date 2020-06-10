package com.android.exercise.ui.activity.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.dimeno.adapter.callback.OnItemClickCallback;
import com.wangzhen.refresh.RefreshLayout;
import com.wangzhen.refresh.callback.OnRefreshCallback;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreActivity extends BaseActivity implements OnRefreshCallback {

    private RefreshLayout mRefresh;
    private RecyclerView mRecycler;
    private LoadMoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        fitDarkStatus(true);

        mRefresh = (RefreshLayout) findViewById(R.id.refresh);
        mRefresh.setOnRefreshCallback(this);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        request();
    }

    private void request() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i + 1));
        }
        mAdapter = new LoadMoreAdapter(list, mRecycler);
        mAdapter.setOnClickCallback(new OnItemClickCallback() {
            @Override
            public void onItemClick(View itemView, int position) {
                String s = mAdapter.getDatas().get(position);
                Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                request();
                mRefresh.refreshComplete();
            }
        }, 500);
    }
}