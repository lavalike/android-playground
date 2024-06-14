package com.android.playground.ui.activity.jetpack.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * PersonDatabase
 * Created by wangzhen on 2020/6/3.
 */
@Database(entities = {Person.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract PersonDao personDao();

    private static PersonDatabase sInstance;

    public static PersonDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (PersonDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, "person_database").build();
                }
            }
        }
        return sInstance;
    }
}
