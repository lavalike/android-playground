package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aliya.permission.Permission;
import com.aliya.permission.PermissionCallback;
import com.aliya.permission.PermissionManager;
import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.IKey;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * VideoRecordActivity 录视频
 * Created by wangzhen on 2019/1/30.
 */
public class VideoRecordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_camera_video));
    }

    @OnClick({R.id.btn_system, R.id.btn_custom})
    public void onClick(final View view) {
        PermissionManager.request(this, new PermissionCallback() {
            @Override
            public void onGranted(boolean isAlready) {
                switch (view.getId()) {
                    case R.id.btn_system:
                        systemCamera();
                        break;
                    case R.id.btn_custom:
                        customCamera();
                        break;
                }
            }

            @Override
            public void onDenied(@NonNull List<String> deniedPermissions, @Nullable List<String> neverAskPermissions) {

            }
        }, Permission.CAMERA, Permission.STORAGE_WRITE, Permission.MICROPHONE_RECORD_AUDIO);
    }

    private void systemCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10485760L);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        startActivity(intent);
    }

    private void customCamera() {
        Intent intent = new Intent(this, LimitedVideoActivity.class);
        intent.putExtra(IKey.VIDEO_RECORD_TIME_LIMITED, 10);
        startActivity(intent);
    }
}
