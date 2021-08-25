package com.android.exercise.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.databinding.ActivityHtmlRichTextBinding;
import com.android.exercise.util.AppUtil;

/**
 * HtmlRichTextActivity
 * Created by wangzhen on 2019-12-06.
 */
public class HtmlRichTextActivity extends BaseActivity {

    private ActivityHtmlRichTextBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityHtmlRichTextBinding.inflate(getLayoutInflater())).getRoot());
        init();
    }

    private void init() {
        String html = AppUtil.getAssetsText("html/rich_text.html");
        binding.tv.setRichText(html);

        binding.tvBrief.setBriefMode(true);
        binding.tvBrief.setRichText(html);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_html_rich_text));
    }
}
