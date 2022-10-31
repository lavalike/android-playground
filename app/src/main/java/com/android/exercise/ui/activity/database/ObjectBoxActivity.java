package com.android.exercise.ui.activity.database;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.domain.objectbox.MyObjectBox;
import com.android.exercise.domain.objectbox.PlayList;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxActivity extends BaseActivity {

    private Box<PlayList> mBox;
    private BoxStore mBoxStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_box);
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        mBox = mBoxStore.boxFor(PlayList.class);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_object_box));
    }

    public void onClick(View view) {
        PlayList playList;
        switch (view.getId()) {
            case R.id.btn_add:
                playList = new PlayList();
                playList.time = System.currentTimeMillis();
                playList.name = "手动";
                mBox.put(playList);
                Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add_batch:
                List<PlayList> list = new ArrayList<>();
                long start = System.currentTimeMillis();
                for (int i = 0; i < 10000; i++) {
                    playList = new PlayList();
                    playList.time = i;
                    playList.name = UUID.randomUUID().toString();
                    list.add(playList);
                }
                mBox.put(list);
                Toast.makeText(mContext, "批量添加" + list.size() + "条，用时" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_del:
                PlayList first = mBox.query().build().findFirst();
                if (first != null) {
                    mBox.remove(first);
                    Toast.makeText(mContext, "删除第一条数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "没有数据了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_del_batch:
                mBox.removeAll();
                Toast.makeText(mContext, "删除全部数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                List<PlayList> playLists = mBox.query().build().find();
                if (playLists.isEmpty()) {
                    Toast.makeText(mContext, "无数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "共" + playLists.size() + "条数据", Toast.LENGTH_SHORT).show();
                    for (PlayList play : playLists) {
                        Log.e("TAG", "|\t" + play.id + "\t|\t" + play.name + "\t|\t" + play.time + "\t|");
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBoxStore != null) {
            mBoxStore.close();
        }
    }
}