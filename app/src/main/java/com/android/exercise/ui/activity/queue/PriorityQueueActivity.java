package com.android.exercise.ui.activity.queue;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import java.util.ArrayList;
import java.util.PriorityQueue;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优先级队列
 * Created by wangzhen on 2019/2/13.
 */
public class PriorityQueueActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_queue);
        ButterKnife.bind(this);

        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student(60, "王震"));
        list.add(new Student(75, "李亚飞"));
        list.add(new Student(90, "汪林杰"));
        list.add(new Student(30, "李欣科"));

        tvContent.setText("原始顺序：\n");
        for (Student student : list) {
            tvContent.append(student.toString());
            tvContent.append("\n");
        }

        tvContent.append("\n-----按分数高低出队选座-----\n");
        PriorityQueue<Student> queue = new PriorityQueue<>();
        for (Student student : list) {
            queue.offer(student);
        }

        while (!queue.isEmpty()) {
            tvContent.append(queue.poll().toString());
            tvContent.append("\n");
        }

    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_priority_queue));
    }
}
