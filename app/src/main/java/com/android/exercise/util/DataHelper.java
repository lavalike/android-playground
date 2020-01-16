package com.android.exercise.util;

import android.support.annotation.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据传递帮助类
 * Created by wangzhen on 2018/3/1.
 */
public class DataHelper {
    private static DataHelper mInstance;
    private Map<String, SoftReference<Object>> mCache = new HashMap<>();

    /**
     * 获取instance
     *
     * @return instance
     */
    public static DataHelper get() {
        if (mInstance == null) {
            synchronized (DataHelper.class) {
                if (mInstance == null) {
                    mInstance = new DataHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 存储value
     *
     * @param key   key
     * @param value value
     */
    public void put(@Nullable String key, @Nullable Object value) {
        mCache.put(key, new SoftReference<>(value));
    }

    /**
     * 根据key取出value，由于GC机制，取到的数据可能为null
     *
     * @param key key
     * @return value
     */
    @Nullable
    public Object getData(@Nullable String key) {
        SoftReference softReference = mCache.get(key);
        return softReference == null ? null : softReference.get();
    }

    /**
     * 根据key移除对应缓存
     *
     * @param key key
     * @return Reference<Object>
     */
    @Nullable
    public Reference<Object> remove(@Nullable String key) {
        return mCache.remove(key);
    }

    /**
     * clear all cache
     */
    public void removeAll() {
        mCache.clear();
    }
}
