package com.android.playground.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * 自动化辅助服务 AccessibilityService
 * Created by wangzhen on 2016/11/25.
 */

public class AutoService extends AccessibilityService {

    private AccessibilityNodeInfo mRootNodes;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (null != event.getSource()) {
            mRootNodes = event.getSource();
            performAction("点击");
        }
    }

    /**
     * 模拟点击带有指定文本的控件
     *
     * @param text
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void performAction(String text) {
        List<AccessibilityNodeInfo> list = mRootNodes.findAccessibilityNodeInfosByText(text);
        if (list != null && !list.isEmpty()) {
            for (AccessibilityNodeInfo node : list) {
                if (node.isEnabled()) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
