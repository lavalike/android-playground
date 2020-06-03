package com.android.exercise.ui.activity.jetpack;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Worker4
 * Created by wangzhen on 2020/6/2.
 */
public class Worker4 extends Worker {
    public Worker4(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("TAG", "-> Worker4.doWork()");
        return Result.success();
    }
}
