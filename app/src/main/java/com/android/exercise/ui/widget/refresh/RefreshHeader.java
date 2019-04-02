package com.android.exercise.ui.widget.refresh;

import android.content.Context;
import android.widget.TextView;

import com.android.exercise.R;
import com.wangzhen.refresh.header.HeaderView;

/**
 * RefreshHeader
 * Created by wangzhen on 2019/3/27.
 */
public class RefreshHeader extends HeaderView {

    private TextView textView;

    private String texts[] = new String[]{"下拉刷新", "释放刷新", "正在刷新", "刷新完成"};


    public RefreshHeader(Context context) {
        super(context);
        inflate(context, R.layout.layout_refresh_header, this);
        textView = findViewById(R.id.tv_tip);
        textView.setText(texts[0]);
    }

    @Override
    public void onTriggerEnter() {
        textView.setText(texts[1]);
    }

    @Override
    public void onTriggerExit() {
        textView.setText(texts[0]);
    }

    @Override
    public void onRefresh() {
        textView.setText(texts[2]);
    }

    @Override
    public void onRefreshComplete() {
        super.onRefreshComplete();
        textView.setText(texts[3]);
    }
}
