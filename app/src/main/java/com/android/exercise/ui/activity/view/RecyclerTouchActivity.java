package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityRecyclerTouchBinding;
import com.android.exercise.listener.RecyclerTouchCallback;
import com.android.exercise.ui.adapter.RecyclerTouchAdapter;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView ItemTouchHelper
 * Created by wangzhen on 2017/8/1.
 */
public class RecyclerTouchActivity extends BaseActivity {
    private ActivityRecyclerTouchBinding binding;
    private ItemTouchHelper itemTouchHelper;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerTouchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRecycler();
        init();
    }

    private void initRecycler() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this, 6);
        recycler = binding.recyclerTouch;
        recycler.setLayoutManager(linearLayoutManager);
    }

    private void init() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        RecyclerTouchAdapter adapter = new RecyclerTouchAdapter(this, list);
        itemTouchHelper = new ItemTouchHelper(new RecyclerTouchCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recycler);
        adapter.setItemLongClickListener((view, data) -> {
            RecyclerView.ViewHolder viewHolder = recycler.getChildViewHolder(view);
            itemTouchHelper.startDrag(viewHolder);
        });
        recycler.setAdapter(adapter);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themedMenu(this, getString(R.string.item_recycler_touch), "切换", () -> {
            RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                recycler.setLayoutManager(gridLayoutManager);
            }
            if (layoutManager instanceof GridLayoutManager) {
                recycler.setLayoutManager(linearLayoutManager);
            }
        });
    }
}
