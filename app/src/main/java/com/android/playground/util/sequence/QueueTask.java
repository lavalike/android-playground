package com.android.playground.util.sequence;

import android.app.Activity;

/**
 * 队列任务Task
 * Created by wangzhen on 2019/2/12.
 */
public abstract class QueueTask {
    private SequenceController controller;
    private Activity activity;

    /**
     * 绑定WindowSequenceController
     *
     * @param controller controller
     */
    void attachController(SequenceController controller) {
        this.controller = controller;
    }


    /**
     * 绑定Activity
     *
     * @param activity activity
     */
    void attachActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取绑定的WindowSequenceController
     *
     * @return controller
     */
    protected SequenceController controller() {
        return controller;
    }

    /**
     * 获取绑定的Activity
     *
     * @return activity
     */
    protected Activity activity() {
        return activity;
    }

    /**
     * 执行具体操作
     */
    protected abstract void run();
}
