package com.android.exercise.domain.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wangzhen on 16/11/7.
 */
@Entity
public class User {
    private String name;
    private int age;

    @Generated(hash = 275175294)
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User() {
    }

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