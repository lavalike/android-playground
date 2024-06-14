package com.android.playground.ui.activity.jetpack.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Worker1
 * Created by wangzhen on 2020/6/2.
 */
public class Worker1 extends Worker {
    public Worker1(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("TAG", "-> Worker1.doWork()");
        return Result.success();
    }
}
