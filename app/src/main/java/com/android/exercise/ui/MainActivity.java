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
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.ItemBean;
import com.android.exercise.ui.activity.AIDLActivity;
import com.android.exercise.ui.activity.AndServerActivity;
import com.android.exercise.ui.activity.Anim24hActivity;
import com.android.exercise.ui.activity.AutoServiceActivity;
import com.android.exercise.ui.activity.ConstraintLayoutActivity;
import com.android.exercise.ui.activity.DispatchActivity;
import com.android.exercise.ui.activity.DrawerSlideActivity;
import com.android.exercise.ui.activity.FlowLayoutActivity;
import com.android.exercise.ui.activity.GreendaoActivity;
import com.android.exercise.ui.activity.MinaActivity;
import com.android.exercise.ui.activity.OKHttpActivity;
import com.android.exercise.ui.activity.RealmActivity;
import com.android.exercise.ui.activity.RecyclerActivity;
import com.android.exercise.ui.activity.RetrofitActivity;
import com.android.exercise.ui.activity.RippleActivity;
import com.android.exercise.ui.activity.SlidingMenuActivity;
import com.android.exercise.ui.activity.ThreadPoolActivity;
import com.android.exercise.ui.adapter.FunctionAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
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
    private List<ItemBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
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
        mList.add(new ItemBean(getString(R.string.item_realm), RealmActivity.class));
        mList.add(new ItemBean(getString(R.string.item_greendao), GreendaoActivity.class));
        mList.add(new ItemBean(getString(R.string.item_retrofit), RetrofitActivity.class));
        mList.add(new ItemBean(getString(R.string.item_okhttp), OKHttpActivity.class));
        mList.add(new ItemBean(getString(R.string.item_loadmore), RecyclerActivity.class));
        mList.add(new ItemBean(getString(R.string.item_slidingmenu), SlidingMenuActivity.class));
        mList.add(new ItemBean(getString(R.string.item_flowlayout), FlowLayoutActivity.class));
        mList.add(new ItemBean(getString(R.string.item_accessibility), AutoServiceActivity.class));
        mList.add(new ItemBean(getString(R.string.item_24hanim), Anim24hActivity.class));
        mList.add(new ItemBean(getString(R.string.item_threadpool), ThreadPoolActivity.class));
        mList.add(new ItemBean(getString(R.string.item_dispatch), DispatchActivity.class));
        mList.add(new ItemBean(getString(R.string.item_constraintlayout), ConstraintLayoutActivity.class));
        mList.add(new ItemBean(getString(R.string.item_drawerslide), DrawerSlideActivity.class));
        mList.add(new ItemBean(getString(R.string.item_andServer), AndServerActivity.class));
        mList.add(new ItemBean(getString(R.string.item_aidl), AIDLActivity.class));
        mList.add(new ItemBean(getString(R.string.item_mina), MinaActivity.class));
        mList.add(new ItemBean(getString(R.string.item_ripple), RippleActivity.class));
        mAdapter = new FunctionAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<ItemBean>() {
            @Override
            public void onItemClick(View view, int position, ItemBean data) {
                Class<?> targetClass = data.getTargetClass();
                if (targetClass != null) {
                    startActivity(new Intent(mContext, targetClass));
                }
            }
        });
        recyclerview.setAdapter(mAdapter);
    }
}
