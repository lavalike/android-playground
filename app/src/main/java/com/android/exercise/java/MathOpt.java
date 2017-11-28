package com.android.exercise.java;

/**
 * 求三角函数
 * Created by wangzhen on 2017/11/28.
 */
public class MathOpt {

    public static void main(String[] args) {
        //求A角度
        int a = 2;//对边
        int b = 2;//斜边
        double tanA = a / b;//正切值
        double radian = Math.atan(tanA);
        System.out.println("弧度角：" + radian);
        double angle = Math.toDegrees(radian);
        System.out.println("角度角：" + angle);
    }
}
