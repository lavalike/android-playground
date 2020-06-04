package com.android.exercise.ui.activity.jetpack.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

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

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, "ViewModel + LiveData");
    }

    public void onClick(View view) {
        mViewModel.setMessage("hello -> " + System.currentTimeMillis());
    }
}