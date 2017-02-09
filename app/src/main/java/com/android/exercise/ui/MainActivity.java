package com.android.exercise.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.activity.Anim24hActivity;
import com.android.exercise.ui.activity.AutoServiceActivity;
import com.android.exercise.ui.activity.FlowLayoutActivity;
import com.android.exercise.ui.activity.GreendaoActivity;
import com.android.exercise.ui.activity.LoadmoreActivity;
import com.android.exercise.ui.activity.OKHttpActivity;
import com.android.exercise.ui.activity.RealmActivity;
import com.android.exercise.ui.activity.RetrofitActivity;
import com.android.exercise.ui.activity.SlidingMenuActivity;
import com.android.exercise.ui.adapter.FunctionAdapter;
import com.android.exercise.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private FunctionAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecycler();
        initList();
    }

    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = DisplayUtil.dip2px(mContext, 0.5f);
                outRect.right = DisplayUtil.dip2px(mContext, 0.5f);
                outRect.top = DisplayUtil.dip2px(mContext, 0.5f);
                outRect.bottom = DisplayUtil.dip2px(mContext, 0.5f);
            }
        });
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.app_name), false);
    }

    /**
     * 添加数据
     */
    private void initList() {
        mList = new ArrayList<>();
        mList.add(getString(R.string.item_realm));
        mList.add(getString(R.string.item_greendao));
        mList.add(getString(R.string.item_retrofit));
        mList.add(getString(R.string.item_okhttp));
        mList.add(getString(R.string.item_loadmore));
        mList.add(getString(R.string.item_slidingmenu));
        mList.add(getString(R.string.item_flowlayout));
        mList.add(getString(R.string.item_accessibility));
        mList.add(getString(R.string.item_24hanim));
        mAdapter = new FunctionAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<String>() {
            @Override
            public void onItemClick(View view, int position, String data) {
                redirect(data);
            }
        });
        recyclerview.setAdapter(mAdapter);
    }

    private void redirect(String data) {
        switch (data) {
            case "Realm":
                startActivity(new Intent(mContext, RealmActivity.class));
                break;
            case "GreenDao":
                startActivity(new Intent(mContext, GreendaoActivity.class));
                break;
            case "Retrofit 2.0":
                startActivity(new Intent(mContext, RetrofitActivity.class));
                break;
            case "OKHttp":
                startActivity(new Intent(mContext, OKHttpActivity.class));
                break;
            case "加载更多":
                startActivity(new Intent(mContext, LoadmoreActivity.class));
                break;
            case "自定义侧滑菜单":
                startActivity(new Intent(mContext, SlidingMenuActivity.class));
                break;
            case "流式布局":
                startActivity(new Intent(mContext, FlowLayoutActivity.class));
                break;
            case "自动化辅助":
                startActivity(new Intent(mContext, AutoServiceActivity.class));
                break;
            case "24小时动画":
                startActivity(new Intent(mContext, Anim24hActivity.class));
                break;
        }

    }
}
