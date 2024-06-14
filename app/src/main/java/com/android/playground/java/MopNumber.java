package com.android.playground.java;

/**
 * 猫扑素数
 * Created by wangzhen on 2017/10/24.
 */
public class MopNumber {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            if (isPrime(i)) {
                if (isMopNumber(i)) {
                    System.out.println(i + "是猫扑素数");
                }
            }
        }
    }

    /**
     * 判断是否为素数
     *
     * @param number
     * @return
     */
    private static boolean isPrime(int number) {
        boolean flag = true;
        if (number < 2) {
            flag = false;
        } else {
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断是否为猫扑数
     *
     * @param i
     * @return
     */
    private static boolean isMopNumber(int i) {
        if (i < 10) {
            return i == 2;
        } else {
            return i % 10 == 3 && isMopNumber(i / 10);
        }
    }
}
