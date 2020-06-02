package com.android.exercise.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.StateButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StateButtonActivity extends BaseActivity {

    @BindView(R.id.btn_state)
    StateButton btnState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_button);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_state_button));
    }

    @OnClick({R.id.btn_state, R.id.btn_reset, R.id.btn_loading, R.id.btn_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                btnState.reset();
                break;
            case R.id.btn_loading:
                btnState.startLoading();
                break;
            case R.id.btn_success:
                btnState.success();
                break;
        }
    }
}
