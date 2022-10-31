package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import androidx.core.content.ContextCompat;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.util.T;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ViewSwitchActivity View轮播
 * Created by wangzhen on 2019-04-19.
 */
public class ViewSwitchActivity extends BaseActivity {

    private static final int MSG_NEXT_SWITCHER = 0;
    private static final int MSG_NEXT_FLIPPER = 1;
    @BindView(R.id.text_switcher)
    TextSwitcher switcher;
    @BindView(R.id.view_flipper)
    ViewFlipper flipper;
    private ArrayList<String> list;
    private int switcherIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_switch);
        ButterKnife.bind(this);
        init();
        initSwitcher();
        initFlipper();
    }

    private void init() {
        list = new ArrayList<>();
        list.add("习近平出席全球理论坛闭幕式并致辞");
        list.add("东体：里皮离任因对国足球管理层不满 当顾问是许家力邀");
//        list.add("火爆上海的马丁哥龙虾来杭州啦！吃货准备好了吗？");
//        list.add("完美世界重磅首发，各种好礼等你来抢啦！");
    }

    private void initSwitcher() {
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return getLayoutInflater().inflate(R.layout.switcher_item_layout, null);
            }
        });
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.get(mContext).toast(list.get(switcherIndex % list.size()));
            }
        });
        handler.sendEmptyMessage(MSG_NEXT_SWITCHER);
    }

    private void initFlipper() {
        TextView view;
        for (int i = 0; i < list.size(); i++) {
            view = new TextView(this);
            view.setText(list.get(i));
            view.setTextColor(ContextCompat.getColor(this, R.color.color_text_color));
            view.setTextSize(18);
            flipper.addView(view);
        }
        flipper.setFlipInterval(1000 * 3);
        if (list.size() > 1) {
            flipper.startFlipping();
        } else {
            flipper.stopFlipping();
        }
    }

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NEXT_SWITCHER:
                    switcher.setText(list.get(switcherIndex % list.size()));
                    if (list.size() > 1) {
                        handler.sendEmptyMessageDelayed(MSG_NEXT_SWITCHER, 3000);
                        switcherIndex++;
                    }
                    break;
                case MSG_NEXT_FLIPPER:
                    break;
            }
        }
    };

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_view_switcher));
    }
}
