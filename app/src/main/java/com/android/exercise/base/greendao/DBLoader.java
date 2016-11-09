package com.android.exercise.base.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.exercise.db.greendao.DaoMaster;
import com.android.exercise.db.greendao.DaoSession;
import com.android.exercise.util.C;

/**
 * GreenDao加载类
 * Created by wangzhen on 16/11/7.
 */

public class DBLoader {

    private static SQLiteDatabase db;
    private static DaoSession mDaoSession;

    public static void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, C.DB_NAME, null);
        db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        mDaoSession = master.newSession();
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static DaoSession getSession() {
        return mDaoSession;
    }
}
