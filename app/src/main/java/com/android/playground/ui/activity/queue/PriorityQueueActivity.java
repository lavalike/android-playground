package com.android.playground.ui.activity.queue;

import android.os.Bundle;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityPriorityQueueBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * 优先级队列
 * Created by wangzhen on 2019/2/13.
 */
public class PriorityQueueActivity extends BaseActivity {
    private ActivityPriorityQueueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityPriorityQueueBinding.inflate(getLayoutInflater())).getRoot());

        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student(60, "王震"));
        list.add(new Student(75, "李亚飞"));
        list.add(new Student(90, "汪林杰"));
        list.add(new Student(30, "李欣科"));

        binding.tvContent.setText("原始顺序：\n");
        for (Student student : list) {
            binding.tvContent.append(student.toString());
            binding.tvContent.append("\n");
        }

        binding.tvContent.append("\n-----按分数高低出队选座-----\n");
        PriorityQueue<Student> queue = new PriorityQueue<>();
        for (Student student : list) {
            queue.offer(student);
        }

        while (!queue.isEmpty()) {
            binding.tvContent.append(queue.poll().toString());
            binding.tvContent.append("\n");
        }

    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_priority_queue));
    }
}
