package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.domain.CommentBean;
import com.android.exercise.ui.adapter.FloorAdapter;
import com.wangzhen.commons.toolbar.impl.Toolbar;

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

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_floor));
    }
}
