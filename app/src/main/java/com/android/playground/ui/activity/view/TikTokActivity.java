package com.android.playground.ui.activity.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.databinding.ActivityTikTokBinding;
import com.android.playground.ui.adapter.TikTokAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 抖音效果
 * Created by wangzhen on 2024/2/23/023
 */
public class TikTokActivity extends BaseActivity {
    private ActivityTikTokBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityTikTokBinding.inflate(getLayoutInflater())).getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerTikTok.setLayoutManager(layoutManager);

        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.bg_1);
        list.add(R.mipmap.bg_2);
        list.add(R.mipmap.bg_3);
        list.add(R.mipmap.bg_4);
        list.add(R.mipmap.bg_5);
        list.add(R.mipmap.bg_6);

        TikTokAdapter tikTokAdapter = new TikTokAdapter(list);
        binding.recyclerTikTok.setAdapter(tikTokAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerTikTok);
    }
}
