package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarRightTextHolder;
import com.android.exercise.listener.RecyclerTouchCallback;
import com.android.exercise.ui.adapter.RecyclerTouchAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView ItemTouchHelper
 * Created by wangzhen on 2017/8/1.
 */
public class RecyclerTouchActivity extends BaseActivity {

    @BindView(R.id.recycler_touch)
    RecyclerView mRecyclerView;
    private RecyclerTouchAdapter mAdapter;
    private ItemTouchHelper itemTouchHelper;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_touch);
        ButterKnife.bind(this);
        initRecycler();
        init();
    }

    private void initRecycler() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this, 6);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void init() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        mAdapter = new RecyclerTouchAdapter(this, list);
        itemTouchHelper = new ItemTouchHelper(new RecyclerTouchCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter.setItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onLongClick(View view, int position, String data) {
                if (position == 0) return;
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
                itemTouchHelper.startDrag(viewHolder);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        ToolBarRightTextHolder holder = new ToolBarRightTextHolder(this, toolbar, getString(R.string.item_recycler_touch), "切换");
        holder.findById(R.id.tv_toolbar_right_text_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                }
                if (layoutManager instanceof GridLayoutManager) {
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                }
            }
        });
    }
}
