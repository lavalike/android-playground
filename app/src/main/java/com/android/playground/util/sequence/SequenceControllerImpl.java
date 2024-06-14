package com.android.playground.util.sequence;

import android.app.Activity;
import android.os.Handler;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 弹窗顺序执行控制器
 * Created by wangzhen on 2019/2/12.
 */
public class SequenceControllerImpl implements SequenceController {
    private Activity activity;
    //任务队列
    private Queue<QueueTask> mTaskQueue = new LinkedList<>();

    private Handler handler = new Handler();

    public SequenceControllerImpl(Activity activity) {
        if (activity == null)
            throw new NullPointerException("null activity is not permitted");
        this.activity = activity;
    }

    /**
     * 任务入队
     *
     * @param task task
     */
    @Override
    public void enqueue(QueueTask task) {
        task.attachController(this);
        task.attachActivity(activity);
        mTaskQueue.offer(task);
    }

    /**
     * 任务出队
     *
     * @param task task
     */
    @Override
    public void dequeue(QueueTask task) {
        mTaskQueue.remove(task);
    }

    /**
     * 出队并执行任务
     */
    @Override
    public void proceed() {
        if (!mTaskQueue.isEmpty()) {
            mTaskQueue.poll().run();
        }
    }
}
