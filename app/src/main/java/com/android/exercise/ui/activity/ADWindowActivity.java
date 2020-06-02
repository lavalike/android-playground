package com.android.exercise.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.dialog.ADWindowDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ADWindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adwindow);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_ad_window));
    }

    @OnClick(R.id.btn_ad)
    public void onViewClicked() {
        ADWindowDialog adWindowDialog = new ADWindowDialog();
        adWindowDialog.showDialog(getFragmentManager());
    }
}
