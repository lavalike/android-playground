package com.android.playground.ui.activity.jetpack.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * PersonDao
 * Created by wangzhen on 2020/6/3.
 */
@Dao
public interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Person person);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<Person> persons);

    @Delete
    public void delete(Person person);

    @Delete
    public void delete(List<Person> persons);

    @Update
    public void update(Person person);

    @Query("select * from person_table")
    public List<Person> loadAllPersons();
}
