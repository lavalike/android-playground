package com.android.playground.java.countdown;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * ValidationCode 验证码倒计时
 * Created by wangzhen on 2019/3/11.
 */
public class ValidationCode {

    private CountDownLatch latch;
    private Timer timer;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("已发送验证码");

        ValidationCode validationCode = new ValidationCode();
        validationCode.doWork();

        System.out.println("点击重新发送");
    }

    private void doWork() throws InterruptedException {
        latch = new CountDownLatch(10);
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
        latch.await();
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            long count = latch.getCount();
            if (count > 0) {
                System.out.println("重新发送(" + count + ")");
                latch.countDown();
            } else {
                timer.cancel();
                timerTask.cancel();
            }
        }
    };
}
