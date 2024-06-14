package com.android.playground.ui.activity.media;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityVideoRecordBinding;
import com.android.playground.util.IKey;
import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.wangzhen.permission.PermissionManager;
import com.wangzhen.permission.callback.AbsPermissionCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * VideoRecordActivity 录视频
 * Created by wangzhen on 2019/1/30.
 */
public class VideoRecordActivity extends BaseActivity {
    private ActivityVideoRecordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityVideoRecordBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_camera_video));
    }

    public void setEvents() {
        binding.btnSystem.setOnClickListener(view -> {
            PermissionManager.request(this, new AbsPermissionCallback() {
                @Override
                public void onGrant(@NonNull String[] strings) {
                    systemCamera();
                }

                @Override
                public void onDeny(@NonNull String[] strings, @NonNull String[] strings1) {

                }
            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
        });
        binding.btnCustom.setOnClickListener(view -> {
            PermissionManager.request(this, new AbsPermissionCallback() {
                @Override
                public void onGrant(@NonNull String[] strings) {
                    customCamera();
                }

                @Override
                public void onDeny(@NonNull String[] strings, @NonNull String[] strings1) {

                }
            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
        });
    }

    private void systemCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //API24需要使用FileProvider
        Uri uriForFile = FileProvider.getUriForFile(this, "com.android.playground.fileProvider", new File(getDefaultPath()));
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
