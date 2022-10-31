package com.android.exercise.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.adapter.HoverAdapter;
import com.android.exercise.ui.adapter.decoration.HoverDecoration;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分组悬停
 * Created by wangzhen on 2018/6/4.
 */
public class HoverRecyclerViewActivity extends BaseActivity implements HoverDecoration.HoverCallback {
    @BindView(R.id.recycler_hover)
    RecyclerView recyclerView;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover_recycler_view);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        HoverDecoration hoverDecoration = new HoverDecoration(this);
        hoverDecoration.setCallback(this);
        recyclerView.addItemDecoration(hoverDecoration);

        list = new ArrayList<>();
        for (int i = 1; i <= 110; i++) {
            list.add(i);
        }
        HoverAdapter adapter = new HoverAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_recycler_hover));
    }

    @Override
    public String getGroupName(int position) {
        String name = "";
        if (list != null && !list.isEmpty()) {
            if (position > -1 && position < list.size()) {
                int data = list.get(position);
                if (data < 0) {
                    name = "minus";
                } else if (data <= 10) {
                    name = "1~10";
                } else if (data <= 20) {
                    name = "11~20";
                } else if (data <= 30) {
                    name = "21~30";
                } else if (data <= 40) {
                    name = "31~40";
                } else if (data <= 50) {
                    name = "41~50";
                } else if (data <= 60) {
                    name = "51~60";
                } else if (data <= 70) {
                    name = "61~70";
                } else if (data <= 80) {
                    name = "71~80";
                } else if (data <= 90) {
                    name = "81~90";
                } else if (data <= 100) {
                    name = "91~100";
                } else {
                    name = "101~∞";
                }
            }
        }
        return name;
    }
}
