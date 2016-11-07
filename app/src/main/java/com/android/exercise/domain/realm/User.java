package com.android.exercise.domain.realm;

import io.realm.RealmObject;

/**
 * Created by wangzhen on 16/11/7.
 */

public class User extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
