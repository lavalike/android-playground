package com.android.exercise.domain;

/**
 * Created by wangzhen on 2017/2/24.
 */

public class ItemBean extends BaseBean {
    private String itemName;
    private Class<?> targetClass;

    public ItemBean(String itemName, Class<?> targetClass) {
        this.itemName = itemName;
        this.targetClass = targetClass;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
}
