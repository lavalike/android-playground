package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * InvokeAppActivity
 * Created by wangzhen on 2018/11/16.
 */
public class InvokeAppActivity extends BaseActivity {

    @BindView(R.id.input_intent)
    EditText inputIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke_app);
        ButterKnife.bind(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_invoke_app));
    }

    @OnClick(R.id.btn_invoke)
    public void onViewClicked() {
        String scheme = inputIntent.getText().toString();
        if (!TextUtils.isEmpty(scheme)) {
            try {
                Intent intent = Intent.parseUri(scheme, Intent.URI_INTENT_SCHEME);
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
