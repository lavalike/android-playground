package com.android.exercise.domain;

/**
 * Created by wangzhen on 2017/2/24.
 */

public class ItemBean extends BaseBean {
    public static final int TYPE_TITLE = -10000;
    public static final int TYPE_ITEM = -10001;

    private int itemType;
    private String itemName;
    private Class<?> targetClass;

    public ItemBean(String itemTitle) {
        this.itemType = TYPE_TITLE;
        this.itemName = itemTitle;
    }

    public ItemBean(String itemName, Class<?> targetClass) {
        this.itemType = TYPE_ITEM;
        this.itemName = itemName;
        this.targetClass = targetClass;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
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
