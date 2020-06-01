package com.android.exercise.ui.activity.database;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.objectbox.MyObjectBox;
import com.android.exercise.domain.objectbox.PlayList;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ObjectBoxActivity extends BaseActivity {

    private Box<PlayList> mBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_box);
        mBox = MyObjectBox.builder().androidContext(this).build().boxFor(PlayList.class);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_object_box));
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
                for (int i = 0; i < 100; i++) {
                    playList = new PlayList();
                    playList.time = System.currentTimeMillis();
                    playList.name = "批量：" + i;
                    list.add(playList);
                }
                mBox.put(list);
                Toast.makeText(mContext, "批量添加" + list.size() + "条", Toast.LENGTH_SHORT).show();
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
}