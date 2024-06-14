package com.android.playground.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityFloorBinding;
import com.android.playground.domain.CommentBean;
import com.android.playground.ui.adapter.FloorAdapter;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论盖楼
 * Created by wangzhen on 2017/8/10.
 */
public class FloorActivity extends BaseActivity {
    private ActivityFloorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityFloorBinding.inflate(getLayoutInflater())).getRoot());
        config();
        init();
    }

    private void config() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerFloor.setLayoutManager(layoutManager);
    }

    private void init() {
        List<CommentBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new CommentBean());
        }
        binding.recyclerFloor.setAdapter(new FloorAdapter(list));
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_floor));
    }
}
