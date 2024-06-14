package com.android.playground.java;

public class Money {
    private int days = 365 * 9;
    private double money = 1000;
    private double rate = 0.0005;
    private double interest = 0;

    public static void main(String[] args) {
        System.out.println("***** 中国银行信用卡利息计算 *****\n");
        Money money = new Money();
        money.way1();
        System.out.println();
        money.way2();
        System.out.println("\n***** 中国银行信用卡利息计算 *****");
    }

    /**
     * 不复利
     */
    private void way1() {
        init();
        System.out.println("1、只计算原本金");
        for (int i = 0; i < days; i++) {
            interest = interest + (money * rate);
        }
        System.out.println("应还本金：" + money);
        System.out.println("逾期天数：" + days);
        System.out.println("利息利率：" + (rate * 100) + "%");
        System.out.println("利息总额：" + interest);
        System.out.println("本息总额：" + (money + interest));
    }

    private void way2() {
        init();
        System.out.println("2、复利方式");
        for (int i = 0; i < days; i++) {
            money = money + (money * rate);
        }
        System.out.println("逾期天数：" + days);
        System.out.println("利息利率：" + (rate * 100) + "%");
        System.out.println("本息总额：" + money);
    }

    private void init() {
        days = 365 * 9;
        money = 1000;
        rate = 0.0005;
        interest = 0;
    }
}
