package com.android.exercise.ui.activity.jetpack.work;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * WorkManagerActivity
 *
 * @author: zhen51.wang
 * @date: 2020/06/03
 */
public class WorkManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_work_manager));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one_time:
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Worker1.class)
                        .setConstraints(new Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build())
                        .build();
                WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
                break;
            case R.id.btn_periodic:
                /**
                 * 最小间隔15分钟
                 * @see androidx.work.impl.model.WorkSpec#setPeriodic(long)
                 *
                 * public void setPeriodic(long intervalDuration) {
                 *         if (intervalDuration < MIN_PERIODIC_INTERVAL_MILLIS) {
                 *             Logger.get().warning(TAG, String.format(
                 *                     "Interval duration lesser than minimum allowed value; Changed to %s",
                 *                     MIN_PERIODIC_INTERVAL_MILLIS));
                 *             intervalDuration = MIN_PERIODIC_INTERVAL_MILLIS;
                 *         }
                 *         setPeriodic(intervalDuration, intervalDuration);
                 *     }
                 */
                PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(Worker2.class, 2, TimeUnit.SECONDS)
                        .setConstraints(new Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build())
                        .addTag(Worker2.class.getName())
                        .build();
                WorkManager.getInstance(this).enqueueUniquePeriodicWork(Worker2.class.getName(), ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
                break;
            case R.id.btn_sequence_run:
                WorkManager.getInstance(this)
                        .beginWith(new OneTimeWorkRequest.Builder(Worker1.class).build())
                        .then(new OneTimeWorkRequest.Builder(Worker3.class).build())
                        .then(new OneTimeWorkRequest.Builder(Worker5.class).build())
                        .then(new OneTimeWorkRequest.Builder(Worker2.class).build())
                        .then(new OneTimeWorkRequest.Builder(Worker4.class).build())
                        .enqueue();
                break;
            case R.id.btn_combine_run:
                WorkManager instance = WorkManager.getInstance(this);
                WorkContinuation chain1 = instance.beginWith(new OneTimeWorkRequest.Builder(Worker1.class).build()).then(new OneTimeWorkRequest.Builder(Worker2.class).build());
                WorkContinuation chain2 = instance.beginWith(new OneTimeWorkRequest.Builder(Worker4.class).build()).then(new OneTimeWorkRequest.Builder(Worker5.class).build());
                WorkContinuation combine = WorkContinuation.combine(Arrays.asList(chain1, chain2)).then(new OneTimeWorkRequest.Builder(Worker3.class).build());
                combine.enqueue();
                break;
        }
    }
}