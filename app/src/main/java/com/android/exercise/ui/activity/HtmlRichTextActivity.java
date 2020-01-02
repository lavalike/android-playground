package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.richtext.RichTextView;

/**
 * HtmlRichTextActivity
 * Created by wangzhen on 2019-12-06.
 */
public class HtmlRichTextActivity extends BaseActivity {

    private RichTextView mTextView, mTextViewBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_rich_text);
        mTextView = (RichTextView) findViewById(R.id.tv);
        mTextViewBrief = (RichTextView) findViewById(R.id.tv_brief);
        init();
    }

    private void init() {
        String html = "<b>加粗标题</b>" +
                "您好，该问题已经经过处理，请耐心等待解决。" +
                "<a href='https://www.baidu.com/'><span style='color:#F18E1A;'>点击阅读整体报告</span></a>" +
                "<br/>" +
                "<img src='http://source.upupoo.com/theme/2000048162/previewFix.jpg' /> " +
                "<img src='http://i0.hdslb.com/bfs/archive/3a6ac42872d54e30d186863c4df02e51d8ab37cd.jpg' /> " +
                "<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576038270766&di=e722f0ffc502b78c98e940aa8446574f&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-10-23%2F59ed9ad97d9cc.jpg' /> " +
                "<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576038414575&di=6f31266e2cb47ffbae694b315927fcfc&imgtype=0&src=http%3A%2F%2Fsource.upupoo.com%2Ftheme%2F2000080393%2Fpreview.jpg' /> " +
                "<br/>" +
                "<a href='https://www.baidu.com/'><span style='color:#F18E1A;'>点击阅读整体报告</span></a>" +
                "<br/>" +
                "<a href='https://www.jianshu.com/p/8b89546d2c48'>Alpha值转16进制</a>";
        mTextView.setRichText(html);

        mTextViewBrief.setBriefMode(true);
        mTextViewBrief.setRichText(html);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_html_rich_text));
    }
}
