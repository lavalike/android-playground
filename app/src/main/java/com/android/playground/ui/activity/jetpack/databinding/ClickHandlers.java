package com.android.playground.ui.activity.jetpack.databinding;

import android.view.View;
import android.widget.Toast;

/**
 * ClickHandlers
 * Created by wangzhen on 2020/6/4.
 */
public class ClickHandlers {
    public void onClickAge(View view) {
        Toast.makeText(view.getContext(), "you clicked age", Toast.LENGTH_SHORT).show();
    }
}
