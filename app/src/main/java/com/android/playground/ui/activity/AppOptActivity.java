package com.android.playground.ui.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.service.QQAutoService;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.List;

/**
 * 调起QQ发送消息
 * Created by wangzhen on 2018/12/4.
 */
public class AppOptActivity extends BaseActivity implements View.OnClickListener {

    private static final String QQ_SERVICE_NAME = "com.android.playground/.service.QQAutoService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_opt);
        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_qq).setOnClickListener(this);
        findViewById(R.id.btn_qq_group).setOnClickListener(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_qq_auto_msg));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                startAccessibilityService();
                break;
            case R.id.btn_qq:
                if (isServiceRunning(QQ_SERVICE_NAME)) {
                    Intent intent = new Intent(this, QQAutoService.class);
                    intent.putExtra("type", QQAutoService.TYPE_PERSON);
                    intent.putExtra("message", "Person " + System.currentTimeMillis());
                    startService(intent);
                    personChat("962453221");
                } else {
                    startAccessibilityService();
                }
                break;
            case R.id.btn_qq_group:
                if (isServiceRunning(QQ_SERVICE_NAME)) {
                    Intent intent = new Intent(this, QQAutoService.class);
                    intent.putExtra("type", QQAutoService.TYPE_GROUP);
                    intent.putExtra("message", "Group " + System.currentTimeMillis());
                    startService(intent);
                    groupChat("185201202");
                } else {
                    startAccessibilityService();
                }
                break;
        }
    }

    /**
     * 指定的服务是否在运行中
     *
     * @param serviceName name
     * @return true/false
     */
    private boolean isServiceRunning(String serviceName) {
        AccessibilityManager am = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am != null) {
            List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
            if (serviceInfos != null) {
                for (AccessibilityServiceInfo info : serviceInfos) {
                    if (info.getId().equals(serviceName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 目前无法直接开启服务，暂跳转到设置页
     */
    private void startAccessibilityService() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    /**
     * 打开QQ群资料页
     *
     * @param groupNumber qq群号
     */
    private void groupChat(String groupNumber) {
        String url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + groupNumber + "&card_type=group&source=qrcode";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * 打开单人聊天页面
     *
     * @param qqNumber qq号
     */
    private void personChat(String qqNumber) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
