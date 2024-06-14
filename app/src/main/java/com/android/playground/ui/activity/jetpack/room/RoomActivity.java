package com.android.playground.ui.activity.jetpack.room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RoomActivity
 * Created by zhen51.wang on 2020/06/03.
 */
public class RoomActivity extends BaseActivity {

    private PersonDatabase mDatabase;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        textView = (TextView) findViewById(R.id.tv);
        mDatabase = PersonDatabase.getDatabase(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_room));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    Person person = new Person();
                    person.firstName = "震";
                    person.lastName = "王";
                    mDatabase.personDao().insert(person);
                    emitter.onNext("新增1条");
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
                    textView.setText(s);
                });
                break;
            case R.id.btn_insert_batch:
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    List<Person> list = new ArrayList<>();
                    Person person;
                    long start = System.currentTimeMillis();
                    for (int i = 0; i < 10000; i++) {
                        person = new Person();
                        person.firstName = UUID.randomUUID().toString();
                        person.lastName = "张" + i;
                        list.add(person);
                    }
                    mDatabase.personDao().insert(list);
                    String msg = "批量新增" + list.size() + "条，用时" + (System.currentTimeMillis() - start);
                    emitter.onNext(msg);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                    textView.setText(result);
                });
                break;
            case R.id.btn_update:
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    List<Person> list = mDatabase.personDao().loadAllPersons();
                    if (list != null && !list.isEmpty()) {
                        Person person = list.get(0);
                        person.firstName = "更新：" + person.firstName;
                        person.lastName = "更新：" + person.lastName;
                        mDatabase.personDao().update(person);
                        emitter.onNext(person.toString());
                    } else {
                        emitter.onNext("无数据，更新失败");
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                    textView.setText(result);
                });
                break;
            case R.id.btn_delete:
                Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                    List<Person> list = mDatabase.personDao().loadAllPersons();
                    boolean result;
                    if (list != null && !list.isEmpty()) {
                        mDatabase.personDao().delete(list);
                        result = true;
                    } else {
                        result = false;
                    }
                    emitter.onNext(result);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                    textView.setText(result ? "删除成功" : "删除失败");
                });
                break;
            case R.id.btn_query:
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    List<Person> list = mDatabase.personDao().loadAllPersons();
                    if (list == null || list.isEmpty()) {
                        emitter.onNext("无数据");
                    } else {
                        StringBuilder builder = new StringBuilder();
                        builder.append("共计").append(list.size()).append("条\n");
                        builder.append("|\tid\t|\tfirst_name\t|\tlast_name\t|");
                        for (Person person : list) {
                            if (builder.length() > 0) {
                                builder.append("\n");
                            }
                            builder.append("|\t");
                            builder.append(person.id);
                            builder.append("\t|");

                            builder.append("\t");
                            builder.append(person.lastName);
                            builder.append("\t|");

                            builder.append("\t");
                            builder.append(person.firstName);
                            builder.append("\t|");
                        }
                        emitter.onNext(builder.toString());
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                    textView.setText(result);
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
}