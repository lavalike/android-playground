package com.android.exercise.domain.greendao;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by wangzhen on 16/11/7.
 */
@Table(name = "Man")
public class Man {
    @Column(isId = true, name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}