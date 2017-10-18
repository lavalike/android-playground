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
import com.android.exercise.ui.activity.AnimViewActivity;
import com.android.exercise.ui.activity.AutoServiceActivity;
import com.android.exercise.ui.activity.ConstraintLayoutActivity;
import com.android.exercise.ui.activity.DispatchActivity;
import com.android.exercise.ui.activity.DrawerSlideActivity;
import com.android.exercise.ui.activity.EditViewActivity;
import com.android.exercise.ui.activity.FloorActivity;
import com.android.exercise.ui.activity.FlowLayoutActivity;
import com.android.exercise.ui.activity.FrameAnimationActivity;
import com.android.exercise.ui.activity.GreendaoActivity;
import com.android.exercise.ui.activity.MinaActivity;
import com.android.exercise.ui.activity.OKHttpActivity;
import com.android.exercise.ui.activity.PorterDuffActivity;
import com.android.exercise.ui.activity.RealmActivity;
import com.android.exercise.ui.activity.RecorderActivity;
import com.android.exercise.ui.activity.RecyclerActivity;
import com.android.exercise.ui.activity.RecyclerTouchActivity;
import com.android.exercise.ui.activity.RetrofitActivity;
import com.android.exercise.ui.activity.RippleActivity;
import com.android.exercise.ui.activity.RxJavaActivity;
import com.android.exercise.ui.activity.ScrollActivity;
import com.android.exercise.ui.activity.SlidingMenuActivity;
import com.android.exercise.ui.activity.TextLinkActivity;
import com.android.exercise.ui.activity.ThreadPoolActivity;
import com.android.exercise.ui.activity.ViewActivity;
import com.android.exercise.ui.activity.ViewAnimationActivity;
import com.android.exercise.ui.activity.WaveActivity;
import com.android.exercise.ui.activity.WindowActivity;
import com.android.exercise.ui.adapter.FunctionAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.util.UIUtils;

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
        final GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter != null) {
                    int type = mAdapter.getItemViewType(position);
                    if (type == ItemBean.TYPE_TITLE) {
                        return manager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
                return 1;
            }
        });
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = UIUtils.dip2px(mContext, 0.5f);
                outRect.right = UIUtils.dip2px(mContext, 0.5f);
                outRect.top = UIUtils.dip2px(mContext, 0.5f);
                outRect.bottom = UIUtils.dip2px(mContext, 0.5f);
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
        mList.add(new ItemBean("动画基础"));
        mList.add(new ItemBean(getString(R.string.item_view_animation), ViewAnimationActivity.class));
        mList.add(new ItemBean(getString(R.string.item_frame_animation), FrameAnimationActivity.class));
        mList.add(new ItemBean("自定义控件"));
        mList.add(new ItemBean(getString(R.string.item_porter_duff), PorterDuffActivity.class));
        mList.add(new ItemBean(getString(R.string.item_scrollview), ScrollActivity.class));
        mList.add(new ItemBean(getString(R.string.item_anim_view), AnimViewActivity.class));
        mList.add(new ItemBean(getString(R.string.item_wave_view), WaveActivity.class));
        mList.add(new ItemBean(getString(R.string.item_edit_view), EditViewActivity.class));
        mList.add(new ItemBean(getString(R.string.item_floor), FloorActivity.class));
        mList.add(new ItemBean(getString(R.string.item_recorder), RecorderActivity.class));
        mList.add(new ItemBean(getString(R.string.item_ripple), RippleActivity.class));
        mList.add(new ItemBean(getString(R.string.item_view), ViewActivity.class));
        mList.add(new ItemBean(getString(R.string.item_text_link), TextLinkActivity.class));
        mList.add(new ItemBean("布局类"));
        mList.add(new ItemBean(getString(R.string.item_recycler_touch), RecyclerTouchActivity.class));
        mList.add(new ItemBean(getString(R.string.item_window), WindowActivity.class));
        mList.add(new ItemBean(getString(R.string.item_slidingmenu), SlidingMenuActivity.class));
        mList.add(new ItemBean(getString(R.string.item_loadmore), RecyclerActivity.class));
        mList.add(new ItemBean(getString(R.string.item_flowlayout), FlowLayoutActivity.class));
        mList.add(new ItemBean(getString(R.string.item_24hanim), Anim24hActivity.class));
        mList.add(new ItemBean(getString(R.string.item_drawerslide), DrawerSlideActivity.class));
        mList.add(new ItemBean(getString(R.string.item_constraintlayout), ConstraintLayoutActivity.class));
        mList.add(new ItemBean(getString(R.string.item_accessibility), AutoServiceActivity.class));
        mList.add(new ItemBean("数据库"));
        mList.add(new ItemBean(getString(R.string.item_realm), RealmActivity.class));
        mList.add(new ItemBean(getString(R.string.item_greendao), GreendaoActivity.class));
        mList.add(new ItemBean("Rx系列"));
        mList.add(new ItemBean(getString(R.string.item_rxjava), RxJavaActivity.class));
        mList.add(new ItemBean("网络请求"));
        mList.add(new ItemBean(getString(R.string.item_retrofit), RetrofitActivity.class));
        mList.add(new ItemBean(getString(R.string.item_okhttp), OKHttpActivity.class));
        mList.add(new ItemBean("其他开源库"));
        mList.add(new ItemBean(getString(R.string.item_andServer), AndServerActivity.class));
        mList.add(new ItemBean(getString(R.string.item_aidl), AIDLActivity.class));
        mList.add(new ItemBean(getString(R.string.item_mina), MinaActivity.class));
        mList.add(new ItemBean("其他"));
        mList.add(new ItemBean(getString(R.string.item_threadpool), ThreadPoolActivity.class));
        mList.add(new ItemBean(getString(R.string.item_dispatch), DispatchActivity.class));
        mAdapter = new FunctionAdapter(mContext, mList);
        mAdapter.setItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ItemBean>() {
            @Override
            public void onClick(View view, int position, ItemBean data) {
                if (mAdapter.getItemViewType(position) == ItemBean.TYPE_ITEM) {
                    Class<?> targetClass = data.getTargetClass();
                    if (targetClass != null) {
                        startActivity(new Intent(mContext, targetClass));
                    }
                }
            }
        });
        recyclerview.setAdapter(mAdapter);
    }
}
