package com.android.playground.java.countdown;

import android.os.CountDownTimer;

/**
 * ValidationCode2 验证码倒计时2 Android特有
 * Created by wangzhen on 2019/3/11.
 */
public class ValidationCode2 {

    public static void main(String[] args) {
        System.out.println("已发送验证码");

        new ValidationCountTimer(10 * 1000, 1000).start();
    }

    static class ValidationCountTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        ValidationCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            System.out.println("重新发送(" + (millisUntilFinished / 1000) + ")");
        }

        @Override
        public void onFinish() {
            System.out.println("点击重新发送");
        }
    }
}
