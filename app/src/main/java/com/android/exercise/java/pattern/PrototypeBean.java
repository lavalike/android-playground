package com.android.exercise.java.pattern;

import java.util.Arrays;

public class PrototypeBean implements Cloneable {

    private String title;
    private String content;
    private int[] array;
    private Bean bean;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "PrototypeBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", array=" + Arrays.toString(array) +
                ", bean=" + bean +
                '}';
    }
}
