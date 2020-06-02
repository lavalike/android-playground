package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            public void onItemLongClick(View view, String data) {
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
