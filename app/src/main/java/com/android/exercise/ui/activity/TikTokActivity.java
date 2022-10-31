package com.android.exercise.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.ui.adapter.TikTokAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 抖音效果
 */
public class TikTokActivity extends BaseActivity {
    @BindView(R.id.recycler_tik_tok)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tik_tok);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.bg_1);
        list.add(R.mipmap.bg_2);
        list.add(R.mipmap.bg_3);
        list.add(R.mipmap.bg_4);
        list.add(R.mipmap.bg_5);
        list.add(R.mipmap.bg_6);

        TikTokAdapter tikTokAdapter = new TikTokAdapter(this, list);
        recyclerView.setAdapter(tikTokAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
