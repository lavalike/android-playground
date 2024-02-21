package com.android.exercise.ui.activity.queue;

import android.os.Bundle;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityTaskSequenceBinding;
import com.android.exercise.util.sequence.SequenceController;
import com.android.exercise.util.sequence.SequenceControllerImpl;
import com.android.exercise.util.sequence.task.Task1;
import com.android.exercise.util.sequence.task.Task2;
import com.android.exercise.util.sequence.task.Task3;
import com.android.exercise.util.sequence.task.Task4;
import com.android.exercise.util.sequence.task.Task5;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * 任务顺序执行控制 TaskSequenceActivity
 * Created by wangzhen on 2019/2/12.
 */
public class TaskSequenceActivity extends BaseActivity {
    private ActivityTaskSequenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityTaskSequenceBinding.inflate(getLayoutInflater())).getRoot());
        binding.btnRun.setOnClickListener(v -> {
            SequenceController controller = new SequenceControllerImpl(this);
            controller.enqueue(new Task1(callback));
            controller.enqueue(new Task2(callback));
            controller.enqueue(new Task3(callback));
            controller.enqueue(new Task4(callback));
            controller.enqueue(new Task5(callback));
            controller.proceed();
        });
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_task_sequence));
    }

    Callback callback = new Callback() {
        @Override
        public void update(String msg) {
            binding.tvResult.append(msg);
            binding.tvResult.append("\n");
        }
    };

    public interface Callback {
        void update(String msg);
    }
}
