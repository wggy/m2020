package org.wangep.waitnotify;


import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/9/8 14:29
 */
public class PrintABWithWaitNotify {

    private static final int nums = 100;
    private static final Object obj = new Object();

    public static void main(String[] args) {
        Thread a = new ThreadA();
        Thread b = new ThreadB();
        a.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.start();
    }


    private static class ThreadA extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < nums; i++) {
                synchronized (obj) {
                    System.out.println(i + ": " + "A");
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

        }
    }

    private static class ThreadB extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < nums; i++) {
                synchronized (obj) {
                    System.out.println(i + ": " + "B");
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

        }
    }
}
