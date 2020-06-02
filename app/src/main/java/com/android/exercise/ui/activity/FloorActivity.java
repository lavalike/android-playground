package com.android.exercise.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.CommentBean;
import com.android.exercise.ui.adapter.FloorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 评论盖楼
 * Created by wangzhen on 2017/8/10.
 */
public class FloorActivity extends BaseActivity {

    @BindView(R.id.recycler_floor)
    RecyclerView mRecyclerView;
    private FloorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        ButterKnife.bind(this);
        config();
        init();
    }

    private void config() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void init() {
        List<CommentBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new CommentBean());
        }
        mAdapter = new FloorAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_floor));
    }
}
