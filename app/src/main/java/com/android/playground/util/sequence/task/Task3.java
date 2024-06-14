package com.android.playground.util.sequence.task;

import com.android.playground.ui.activity.queue.TaskSequenceActivity;
import com.android.playground.util.sequence.QueueTask;

public class Task3 extends QueueTask {
    private TaskSequenceActivity.Callback callback;

    public Task3(TaskSequenceActivity.Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void run() {
        callback.update(getClass().getName());
        controller().proceed();
    }
}
