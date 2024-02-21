package com.android.exercise.ui.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityInvokeAppBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.net.URISyntaxException;

/**
 * InvokeAppActivity
 * Created by wangzhen on 2018/11/16.
 */
public class InvokeAppActivity extends BaseActivity {

    private ActivityInvokeAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityInvokeAppBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_invoke_app));
    }

    public void setEvents() {
        binding.btnInvoke.setOnClickListener(v -> {
            String scheme = binding.inputIntent.getText().toString();
            if (!TextUtils.isEmpty(scheme)) {
                try {
                    Intent intent = Intent.parseUri(scheme, Intent.URI_INTENT_SCHEME);
                    startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
