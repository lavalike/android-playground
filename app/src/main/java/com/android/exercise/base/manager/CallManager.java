package com.android.exercise.base.manager;

import java.util.Dictionary;
import java.util.Hashtable;

import retrofit2.Call;

/**
 * Call请求管理类
 * Created by wangzhen on 16/11/9.
 */

public class CallManager {
    private static CallManager mInstance;
    private Dictionary<String, Call> mCallsDict;

    public CallManager() {
        mCallsDict = new Hashtable<>();
    }

    public static CallManager getInstance() {
        if (mInstance == null) {
            synchronized (CallManager.class) {
                if (mInstance == null) {
                    mInstance = new CallManager();
                }
            }
        }
        return mInstance;
    }
}
