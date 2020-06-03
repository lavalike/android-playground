package com.android.exercise.ui.activity.jetpack;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Worker2
 * Created by wangzhen on 2020/6/2.
 */
public class Worker2 extends Worker {
    public Worker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("TAG", "-> Worker2.doWork()");
        return Result.success();
    }
}
