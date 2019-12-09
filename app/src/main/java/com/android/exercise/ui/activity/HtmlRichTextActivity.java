package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.RichTextView;

/**
 * HtmlRichTextActivity
 * Created by wangzhen on 2019-12-06.
 */
public class HtmlRichTextActivity extends BaseActivity {

    private RichTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_rich_text);
        mTextView = (RichTextView) findViewById(R.id.tv);
        init();
    }

    private void init() {
        String html = "<b>加粗标题</b>\n" +
                "您好，该问题已经经过处理，请耐心等待解决。\n" +
                "<a href='https://www.baidu.com/'><span style=\"color:#F18E1A;\">点击阅读整体报告</span></a>\n" +
                "<br/>\n" +
//                "<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575641792484&di=475ec99e4061b371f5cf7fb485f78b80&imgtype=0&src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20180917%2F22%2F1537193274-diLswpUDbH.jpg' />\n" +
                "<img src='https://timgsa.baidu.com/' />\n" +
                "<br/>\n" +
                "<a href='https://www.baidu.com/'><span style=\"color:#F18E1A;\">点击阅读整体报告</span></a>";
        mTextView.setRichText(html);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_html_rich_text));
    }
}
