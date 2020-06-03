package com.android.exercise.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.BaseBean;
import com.android.exercise.domain.ItemBean;
import com.android.exercise.domain.NotificationBean;
import com.android.exercise.domain.TitleBean;
import com.android.exercise.ui.activity.ADWindowActivity;
import com.android.exercise.ui.activity.AIDLActivity;
import com.android.exercise.ui.activity.AndServerActivity;
import com.android.exercise.ui.activity.Anim24hActivity;
import com.android.exercise.ui.activity.AnimViewActivity;
import com.android.exercise.ui.activity.AppOptActivity;
import com.android.exercise.ui.activity.AutoServiceActivity;
import com.android.exercise.ui.activity.BitOptActivity;
import com.android.exercise.ui.activity.BroadcastActivity;
import com.android.exercise.ui.activity.BrowserActivity;
import com.android.exercise.ui.activity.CompileAnnotationActivity;
import com.android.exercise.ui.activity.ConstraintLayoutActivity;
import com.android.exercise.ui.activity.DispatchActivity;
import com.android.exercise.ui.activity.DrawerSlideActivity;
import com.android.exercise.ui.activity.EditViewActivity;
import com.android.exercise.ui.activity.ElasticActivity;
import com.android.exercise.ui.activity.FloorActivity;
import com.android.exercise.ui.activity.FlowLayoutActivity;
import com.android.exercise.ui.activity.FrameAnimationActivity;
import com.android.exercise.ui.activity.HoverRecyclerViewActivity;
import com.android.exercise.ui.activity.HtmlRichTextActivity;
import com.android.exercise.ui.activity.ImageHtmlActivity;
import com.android.exercise.ui.activity.ImageRecyclerActivity;
import com.android.exercise.ui.activity.InvokeAppActivity;
import com.android.exercise.ui.activity.MinaActivity;
import com.android.exercise.ui.activity.NotificationActivity;
import com.android.exercise.ui.activity.OKHttpActivity;
import com.android.exercise.ui.activity.PageSwitchActivity;
import com.android.exercise.ui.activity.PathActivity;
import com.android.exercise.ui.activity.PathMeasureActivity;
import com.android.exercise.ui.activity.PathSearchActivity;
import com.android.exercise.ui.activity.PorterDuffActivity;
import com.android.exercise.ui.activity.RecorderActivity;
import com.android.exercise.ui.activity.RecyclerActivity;
import com.android.exercise.ui.activity.RecyclerTouchActivity;
import com.android.exercise.ui.activity.RefreshLayoutActivity;
import com.android.exercise.ui.activity.RetrofitActivity;
import com.android.exercise.ui.activity.RippleActivity;
import com.android.exercise.ui.activity.RuntimeAnnotationActivity;
import com.android.exercise.ui.activity.RxJavaActivity;
import com.android.exercise.ui.activity.ScrollActivity;
import com.android.exercise.ui.activity.SlidingMenuActivity;
import com.android.exercise.ui.activity.StateButtonActivity;
import com.android.exercise.ui.activity.TextFormatActivity;
import com.android.exercise.ui.activity.TextLinkActivity;
import com.android.exercise.ui.activity.ThreadPoolActivity;
import com.android.exercise.ui.activity.TikTokActivity;
import com.android.exercise.ui.activity.VideoRecordActivity;
import com.android.exercise.ui.activity.ViewActivity;
import com.android.exercise.ui.activity.ViewAnimationActivity;
import com.android.exercise.ui.activity.ViewSwitchActivity;
import com.android.exercise.ui.activity.WaveActivity;
import com.android.exercise.ui.activity.WindowActivity;
import com.android.exercise.ui.activity.jetpack.WorkManagerActivity;
import com.android.exercise.ui.activity.calendar.CalendarActivity;
import com.android.exercise.ui.activity.database.ObjectBoxActivity;
import com.android.exercise.ui.activity.database.RealmActivity;
import com.android.exercise.ui.activity.download.DownloadActivity;
import com.android.exercise.ui.activity.hashmap.HashMapTreeifyActivity;
import com.android.exercise.ui.activity.player.ExoPlayerActivity;
import com.android.exercise.ui.activity.player.IjkPlayerActivity;
import com.android.exercise.ui.activity.queue.PriorityQueueActivity;
import com.android.exercise.ui.activity.queue.TaskSequenceActivity;
import com.android.exercise.ui.activity.stream.FileStreamActivity;
import com.android.exercise.ui.activity.touch.MultiTouchActivity;
import com.android.exercise.ui.adapter.FunctionAdapter;
import com.android.exercise.util.IKey;
import com.android.exercise.util.UIUtils;

import java.io.Serializable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        initPush(getIntent());
        initRecycler();
        initList();
    }

    private void initPush(Intent intent) {
        Serializable extra = intent.getSerializableExtra(IKey.PUSH_DATA);
        if (extra instanceof NotificationBean) {
            showToast(((NotificationBean) extra).getTitle());
        }
    }

    private void initRecycler() {
        final GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter != null) {
                    int type = mAdapter.getItemViewType(position);
                    if (type == FunctionAdapter.TYPE_TITLE) {
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
        mAdapter = new FunctionAdapter(mContext, generateList());
        recyclerview.setAdapter(mAdapter);
    }

    private List<BaseBean> generateList() {
        List<BaseBean> list = new ArrayList<>();
        list.add(new TitleBean("Jetpack"));
        list.add(new ItemBean(getString(R.string.item_work_manager), WorkManagerActivity.class));
        list.add(new TitleBean("数据库"));
        list.add(new ItemBean(getString(R.string.item_object_box), ObjectBoxActivity.class));
        list.add(new ItemBean(getString(R.string.item_realm), RealmActivity.class));
        list.add(new TitleBean("Player"));
        list.add(new ItemBean(getString(R.string.item_exoplayer), ExoPlayerActivity.class));
        list.add(new ItemBean(getString(R.string.item_ijkplayer), IjkPlayerActivity.class));
        list.add(new TitleBean("Html相关"));
        list.add(new ItemBean(getString(R.string.item_html_rich_text), HtmlRichTextActivity.class));
        list.add(new TitleBean("个人开源库"));
        list.add(new ItemBean(getString(R.string.item_download), DownloadActivity.class));
        list.add(new ItemBean(getString(R.string.item_refresh_layout), RefreshLayoutActivity.class));
        list.add(new ItemBean(getString(R.string.item_pull_layout), ElasticActivity.class));
        list.add(new TitleBean("布局类"));
        list.add(new ItemBean(getString(R.string.item_wave_view), WaveActivity.class));
        list.add(new ItemBean(getString(R.string.item_calendar), CalendarActivity.class));
        list.add(new ItemBean(getString(R.string.item_multi_touch), MultiTouchActivity.class));
        list.add(new ItemBean(getString(R.string.item_view_switcher), ViewSwitchActivity.class));
        list.add(new ItemBean(getString(R.string.item_invoke_app), InvokeAppActivity.class));
        list.add(new ItemBean(getString(R.string.item_notification), NotificationActivity.class));
        list.add(new ItemBean(getString(R.string.item_text_format), TextFormatActivity.class));
        list.add(new ItemBean(getString(R.string.item_text_link), TextLinkActivity.class));
        list.add(new ItemBean(getString(R.string.item_state_button), StateButtonActivity.class));
        list.add(new ItemBean(getString(R.string.item_recycler_tik_tok), TikTokActivity.class));
        list.add(new ItemBean(getString(R.string.item_recycler_hover), HoverRecyclerViewActivity.class));
        list.add(new ItemBean(getString(R.string.item_recycler_touch), RecyclerTouchActivity.class));
        list.add(new ItemBean(getString(R.string.item_window), WindowActivity.class));
        list.add(new ItemBean(getString(R.string.item_slidingmenu), SlidingMenuActivity.class));
        list.add(new ItemBean(getString(R.string.item_loadmore), RecyclerActivity.class));
        list.add(new ItemBean(getString(R.string.item_flowlayout), FlowLayoutActivity.class));
        list.add(new ItemBean(getString(R.string.item_24hanim), Anim24hActivity.class));
        list.add(new ItemBean(getString(R.string.item_drawerslide), DrawerSlideActivity.class));
        list.add(new ItemBean(getString(R.string.item_constraintlayout), ConstraintLayoutActivity.class));
        list.add(new ItemBean(getString(R.string.item_ad_window), ADWindowActivity.class));
        list.add(new ItemBean(getString(R.string.item_ice_switch), PageSwitchActivity.class));
        list.add(new ItemBean(getString(R.string.item_porter_duff), PorterDuffActivity.class));
        list.add(new ItemBean(getString(R.string.item_scrollview), ScrollActivity.class));
        list.add(new ItemBean(getString(R.string.item_anim_view), AnimViewActivity.class));
        list.add(new ItemBean(getString(R.string.item_edit_view), EditViewActivity.class));
        list.add(new ItemBean(getString(R.string.item_floor), FloorActivity.class));
        list.add(new ItemBean(getString(R.string.item_recorder), RecorderActivity.class));
        list.add(new ItemBean(getString(R.string.item_ripple), RippleActivity.class));
        list.add(new ItemBean(getString(R.string.item_view), ViewActivity.class));
        list.add(new TitleBean("Stream"));
        list.add(new ItemBean(getString(R.string.item_stream), FileStreamActivity.class));
        list.add(new TitleBean("HashMap"));
        list.add(new ItemBean(getString(R.string.item_hashmap_treeify), HashMapTreeifyActivity.class));
        list.add(new TitleBean("Queue"));
        list.add(new ItemBean(getString(R.string.item_priority_queue), PriorityQueueActivity.class));
        list.add(new ItemBean(getString(R.string.item_task_sequence), TaskSequenceActivity.class));
        list.add(new TitleBean("Camera"));
        list.add(new ItemBean(getString(R.string.item_camera_video), VideoRecordActivity.class));
        list.add(new TitleBean("加载多图"));
        list.add(new ItemBean(getString(R.string.item_image_html), ImageHtmlActivity.class));
        list.add(new ItemBean(getString(R.string.item_image_recycler), ImageRecyclerActivity.class));
        list.add(new TitleBean("位操作"));
        list.add(new ItemBean(getString(R.string.item_bit_opt), BitOptActivity.class));
        list.add(new TitleBean("Invoke"));
        list.add(new ItemBean(getString(R.string.item_qq_auto_msg), AppOptActivity.class));
        list.add(new TitleBean("Path"));
        list.add(new ItemBean(getString(R.string.item_path_search), PathSearchActivity.class));
        list.add(new ItemBean(getString(R.string.item_path_measure), PathMeasureActivity.class));
        list.add(new ItemBean(getString(R.string.item_path), PathActivity.class));
        list.add(new TitleBean("注解"));
        list.add(new ItemBean(getString(R.string.item_annotation_compile), CompileAnnotationActivity.class));
        list.add(new ItemBean(getString(R.string.item_annotation_runtime), RuntimeAnnotationActivity.class));
        list.add(new TitleBean("动画基础"));
        list.add(new ItemBean(getString(R.string.item_view_animation), ViewAnimationActivity.class));
        list.add(new ItemBean(getString(R.string.item_frame_animation), FrameAnimationActivity.class));
        list.add(new TitleBean("开源库"));
        list.add(new ItemBean(getString(R.string.item_rxjava), RxJavaActivity.class));
        list.add(new ItemBean(getString(R.string.item_retrofit), RetrofitActivity.class));
        list.add(new ItemBean(getString(R.string.item_okhttp), OKHttpActivity.class));
        list.add(new ItemBean(getString(R.string.item_andServer), AndServerActivity.class));
        list.add(new ItemBean(getString(R.string.item_mina), MinaActivity.class));
        list.add(new TitleBean("IPC"));
        list.add(new ItemBean(getString(R.string.item_aidl), AIDLActivity.class));
        list.add(new TitleBean("其他"));
        list.add(new ItemBean(getString(R.string.item_accessibility), AutoServiceActivity.class));
        list.add(new ItemBean(getString(R.string.item_webview), BrowserActivity.class));
        list.add(new ItemBean(getString(R.string.item_broadcast), BroadcastActivity.class));
        list.add(new ItemBean(getString(R.string.item_threadpool), ThreadPoolActivity.class));
        list.add(new ItemBean(getString(R.string.item_dispatch), DispatchActivity.class));
        return list;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initPush(intent);
    }
}
