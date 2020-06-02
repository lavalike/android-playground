package com.android.exercise.ui.activity.queue;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.sequence.SequenceController;
import com.android.exercise.util.sequence.SequenceControllerImpl;
import com.android.exercise.util.sequence.task.Task1;
import com.android.exercise.util.sequence.task.Task2;
import com.android.exercise.util.sequence.task.Task3;
import com.android.exercise.util.sequence.task.Task4;
import com.android.exercise.util.sequence.task.Task5;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务顺序执行控制 TaskSequenceActivity
 * Created by wangzhen on 2019/2/12.
 */
public class TaskSequenceActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_sequence);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_task_sequence));
    }

    @OnClick(R.id.btn_run)
    public void onViewClicked() {
        SequenceController controller = new SequenceControllerImpl(this);
        controller.enqueue(new Task1(callback));
        controller.enqueue(new Task2(callback));
        controller.enqueue(new Task3(callback));
        controller.enqueue(new Task4(callback));
        controller.enqueue(new Task5(callback));
        controller.proceed();
    }

    Callback callback = new Callback() {
        @Override
        public void update(String msg) {
            tvResult.append(msg);
            tvResult.append("\n");
        }
    };

    public interface Callback {
        void update(String msg);
    }
}
