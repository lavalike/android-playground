package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityHtmlRichTextBinding;
import com.android.exercise.util.AppUtil;
import com.wangzhen.commons.toolbar.impl.Toolbar;

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

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_html_rich_text));
    }
}
