package com.android.playground.ui.widget;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.android.playground.base.manager.AppManager;

/**
 * 处理TextView中url的点击事件
 * Created by wangzhen on 2017/6/2.
 */
public class LinkClickSpan extends ClickableSpan {
    private String url = "";

    public LinkClickSpan(String url) {
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(AppManager.get().getActivity(), url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#03A9F4"));
        ds.setUnderlineText(false);
    }
}
