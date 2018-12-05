package com.android.exercise.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * QQAutoService
 * Created by wangzhen on 2018/12/4.
 */
public class QQAutoService extends AccessibilityService {
    public static final int TYPE_PERSON = 0;
    public static final int TYPE_GROUP = 1;
    private boolean isExecuted = false;
    private int type;
    private String message;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        type = intent.getIntExtra("type", TYPE_PERSON);
        message = intent.getStringExtra("message");
        if (TextUtils.isEmpty(message)) message = "";
        isExecuted = false;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() != null) {
            if (type == TYPE_PERSON) {
                runQQ();
            }
            if (type == TYPE_GROUP) {
                runQQGroup();
            }
        }
    }

    private void runQQGroup() {
        if (!isExecuted) {
            //查找发消息按钮
            AccessibilityNodeInfo btnSend = findNodeByText("发消息");
            if (btnSend != null && btnSend.isEnabled()) {
                btnSend.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //查找输入框
            AccessibilityNodeInfo nodeInput = findNodeById("com.tencent.mobileqq:id/input");
            if (nodeInput != null) {
                setText(nodeInput, message);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //点击发送按钮
            AccessibilityNodeInfo nodeButton = findNodeById("com.tencent.mobileqq:id/fun_btn");
            if (nodeButton != null && nodeButton.isEnabled()) {
                nodeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            isExecuted = true;
        }
    }

    private void runQQ() {
        if (!isExecuted) {
            //查找输入框
            AccessibilityNodeInfo nodeInput = findNodeById("com.tencent.mobileqq:id/input");
            if (nodeInput != null) {
                setText(nodeInput, message);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //点击发送按钮
            AccessibilityNodeInfo nodeButton = findNodeById("com.tencent.mobileqq:id/fun_btn");
            if (nodeButton != null && nodeButton.isEnabled()) {
                nodeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            isExecuted = true;
        }
    }

    /**
     * 设置文本
     *
     * @param node node
     * @param text text
     */
    private void setText(AccessibilityNodeInfo node, String text) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = new Bundle();
            bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(ClipData.newPlainText("text", text));
                node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                node.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
        }
    }

    /**
     * 根据text查找node
     *
     * @param text text
     * @return node
     */
    private AccessibilityNodeInfo findNodeByText(String text) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText(text);
            if (nodes != null && !nodes.isEmpty()) {
                return nodes.get(0);
            }
        }
        return null;
    }

    /**
     * 根据id查找node
     *
     * @param id id
     * @return node
     */
    private AccessibilityNodeInfo findNodeById(String id) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByViewId(id);
            if (nodes != null && !nodes.isEmpty()) {
                return nodes.get(0);
            }
        }
        return null;
    }

    @Override
    public void onInterrupt() {

    }

}
