package com.android.exercise.domain;

import java.util.regex.Pattern;

/**
 * MapKey HashMap树化Key
 * 重写hashcode，将数字key统一返回1,其他返回2
 * Created by wangzhen on 2019/2/20.
 */
public class MapKey {
    private static final String REG = "[0-9]+";
    private String key;

    public MapKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MapKey mapKey = (MapKey) obj;
        return !(key != null ? !key.equals(mapKey.key) : mapKey.key != null);
    }

    @Override
    public int hashCode() {
        if (key == null)
            return 0;
        Pattern pattern = Pattern.compile(REG);
        if (pattern.matcher(key).matches()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public String toString() {
        return key;
    }
}
