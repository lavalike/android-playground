package com.wangzhen.processors.entity;

import javax.lang.model.element.VariableElement;

/**
 * 被注解的元素信息
 * Created by wangzhen on 2018/5/4.
 */
public class VariableEntity {
    private int id;
    private VariableElement element;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VariableElement getElement() {
        return element;
    }

    public void setElement(VariableElement element) {
        this.element = element;
    }
}
