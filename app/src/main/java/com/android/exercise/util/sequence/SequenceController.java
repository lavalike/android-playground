package com.android.exercise.util.sequence;

/**
 * 顺序控制器接口
 * Created by wangzhen on 2019/2/12.
 */
public interface SequenceController {
    /**
     * 任务入队
     *
     * @param task task
     */
    void enqueue(QueueTask task);

    /**
     * 任务出队
     *
     * @param task task
     */
    void dequeue(QueueTask task);

    /**
     * 出队并执行任务
     */
    void proceed();
}
