package com.android.exercise.ui.activity.jetpack.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class ViewModelActivity extends BaseActivity {

    private TextView textView;
    private CustomViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        textView = (TextView) findViewById(R.id.text);

        mViewModel = new ViewModelProvider(this).get(CustomViewModel.class);
        mViewModel.getMessageLiveData().observe(this, s -> {
            textView.setText(s);
        });
        mViewModel.getMapLiveData().observe(this, length -> {
            Toast.makeText(mContext, "map -> length : " + length, Toast.LENGTH_SHORT).show();
        });
        mViewModel.getSwitchMapLiveData().observe(this, length -> {
            Toast.makeText(mContext, "switch map -> length : " + length, Toast.LENGTH_SHORT).show();
        });
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, "ViewModel + LiveData");
    }

    public void onClick(View view) {
        mViewModel.setMessage("hello -> " + System.currentTimeMillis());
    }
}