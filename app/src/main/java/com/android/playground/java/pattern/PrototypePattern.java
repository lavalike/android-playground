package com.android.playground.java.pattern;

/**
 * 原型模式
 * Created by wangzhen on 2018/11/7.
 */
public class PrototypePattern {

    public static void main(String[] args) throws CloneNotSupportedException {
        PrototypeBean prototypeBean = null;
        Bean bean;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            prototypeBean = new PrototypeBean();
            prototypeBean.setTitle("title");
            prototypeBean.setContent("content");
            prototypeBean.setArray(new int[]{1, 3, 5, 7, 8});
            bean = new Bean();
            bean.setAge(25);
            bean.setName("李亚飞");
            prototypeBean.setBean(bean);
        }
        System.out.println("Prototype new 用时 -> " + (System.currentTimeMillis() - start));
        System.out.println(prototypeBean.toString());
        System.out.println(prototypeBean.hashCode());

        PrototypeBean clone = null;
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            clone = (PrototypeBean) prototypeBean.clone();
        }
        System.out.println("Prototype clone 用时 -> " + (System.currentTimeMillis() - start));
        System.out.println(clone.toString());
        System.out.println(clone.hashCode());
    }
}
