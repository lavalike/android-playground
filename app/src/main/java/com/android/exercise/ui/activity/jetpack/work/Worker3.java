package com.android.exercise.ui.activity.jetpack.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Worker3
 * Created by wangzhen on 2020/6/2.
 */
public class Worker3 extends Worker {
    public Worker3(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("TAG", "-> Worker3.doWork()");
        return Result.success();
    }
}
