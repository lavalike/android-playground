package com.android.exercise.ui.activity.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Person
 * Created by wangzhen on 2020/6/3.
 */
@Entity(tableName = "person_table")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @Ignore
    public String portrait;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}
