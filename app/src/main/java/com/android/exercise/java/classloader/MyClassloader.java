package com.android.exercise.java.classloader;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 * 需要将编译后的Test.class和自定义Classloader放到本项目以外运行
 * Created by wangzhen on 2018/11/7.
 */
public class MyClassloader extends ClassLoader {

    private String classPath;

    public MyClassloader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }

    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream stream = new FileInputStream(classPath + name + ".class");
        byte[] bytes = new byte[stream.available()];
        stream.read(bytes);
        stream.close();
        return bytes;
    }

    public static void main(String[] args) throws Exception {
//        MyClassloader classloader = new MyClassloader("/Users/wangzhen/Android/Git/AndroidExercise/app/src/main/java/");
        MyClassloader classloader = new MyClassloader("/Users/wangzhen/Desktop/classloader/");
        Class<?> clazz = classloader.loadClass("com.android.exercise.java.classloader.Test");
        Method method = clazz.getDeclaredMethod("hello");
        Object o = clazz.newInstance();
        method.invoke(o);
    }
}
