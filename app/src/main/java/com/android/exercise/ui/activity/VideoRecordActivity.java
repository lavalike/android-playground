package com.android.exercise.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.IKey;
import com.wangzhen.permission.PermissionManager;
import com.wangzhen.permission.callback.AbsPermissionCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        PermissionManager.request(this, new AbsPermissionCallback() {

            @Override
            public void onGrant(@NonNull String[] strings) {
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
            public void onDeny(@NonNull String[] strings, @NonNull String[] strings1) {

            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
    }

    private void systemCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //API24需要使用FileProvider
        Uri uriForFile = FileProvider.getUriForFile(this, "com.android.exercise.fileProvider", new File(getDefaultPath()));
        //设置输出目录，默认存储在DCIM
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        //视频质量 0低 1高
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //视频大小限制，单位字节
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024 * 30L);
        startActivity(intent);
    }

    private void customCamera() {
        Intent intent = new Intent(this, LimitedVideoActivity.class);
        intent.putExtra(IKey.VIDEO_RECORD_TIME_LIMITED, 10);
        startActivity(intent);
    }

    /**
     * 获取默认存储路径
     *
     * @return 存储路径
     */
    private String getDefaultPath() {
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return Environment.getExternalStorageDirectory() + File.separator + format.format(time) + ".mp4";
    }
}
