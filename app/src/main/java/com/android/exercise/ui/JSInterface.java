package com.android.exercise.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.exercise.listener.IJSCallback;

import java.util.List;

/**
 * JS
 * Created by wangzhen on 2018/3/15.
 */
public class JSInterface {
    private Context ctx;

    public JSInterface(Context context) {
        ctx = context;
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getAppInfo() {
        return getSysInfo();
    }

    @NonNull
    private String getSysInfo() {
        String result = "";
        try {
            String packageName = "";
            //当前应用pid
            int pid = android.os.Process.myPid();
            //任务管理类
            ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            //遍历所有应用
            List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : infos) {
                if (info.pid == pid) {
                    packageName = info.processName;
                }
            }
            PackageManager packageManager = ctx.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            String label = applicationInfo.loadLabel(packageManager).toString();
            result = "应用名：" + label + "<br/>包名：" + packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @JavascriptInterface
    public void getAppInfoAsync() {
        String appInfo = getSysInfo();
        asyncNotify(appInfo);
    }

    private void asyncNotify(String data) {
        if (ctx instanceof IJSCallback) {
            ((IJSCallback) ctx).exeJS(data);
        }
    }
}
