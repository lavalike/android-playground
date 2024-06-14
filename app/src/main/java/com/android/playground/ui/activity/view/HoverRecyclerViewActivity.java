package com.android.playground.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityHoverRecyclerViewBinding;
import com.android.playground.ui.adapter.HoverAdapter;
import com.android.playground.ui.adapter.decoration.HoverDecoration;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组悬停
 * Created by wangzhen on 2018/6/4.
 */
public class HoverRecyclerViewActivity extends BaseActivity implements HoverDecoration.HoverCallback {
    private ActivityHoverRecyclerViewBinding binding;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityHoverRecyclerViewBinding.inflate(getLayoutInflater())).getRoot());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerHover.setLayoutManager(linearLayoutManager);
        HoverDecoration hoverDecoration = new HoverDecoration(this);
        hoverDecoration.setCallback(this);
        binding.recyclerHover.addItemDecoration(hoverDecoration);

        list = new ArrayList<>();
        for (int i = 1; i <= 110; i++) {
            list.add(i);
        }
        HoverAdapter adapter = new HoverAdapter(list);
        binding.recyclerHover.setAdapter(adapter);
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
