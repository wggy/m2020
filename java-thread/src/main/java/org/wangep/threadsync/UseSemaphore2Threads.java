package org.wangep.threadsync;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * created by wange on 2020/4/9 17:59
 *
 * 使用Semaphore控制并发访问多个资源
 *
 */
public class UseSemaphore2Threads {

    public static void main(String[] args) {
        UseSemaphore2Threads useSemaphore = new UseSemaphore2Threads();
        //创建10个threads。每个线程会执行一个发送文档到print queue的Job对象。
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(useSemaphore), "Thread" + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }


    //1.  这个array储存空闲的等待打印任务的和正在打印文档的printers。
    private boolean freePrinters[];
    //2.  接着，声明一个名为lockPrinters的Lock对象。将要使用这个对象来保护freePrinters array的访问。
    private Lock lockPrinters;
    private final Semaphore semaphore;

    public UseSemaphore2Threads() {
        semaphore = new Semaphore(3);
        freePrinters = new boolean[3];
        for (int i = 0; i < 3; i++) {
            freePrinters[i] = true;
        }
        lockPrinters = new ReentrantLock();
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());
            int assignedPrinter = getPrinter();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job in Printer%d during %d seconds\n", Thread.currentThread().getName(), assignedPrinter, duration);
            TimeUnit.SECONDS.sleep(duration);
            freePrinters[assignedPrinter] = true;
            System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private int getPrinter() {
        int ret = -1;
        try {
            lockPrinters.lock();
            for (int i = 0; i < freePrinters.length; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }

    static class Job implements Runnable {

        private UseSemaphore2Threads useSemaphore;

        public Job(UseSemaphore2Threads useSemaphore) {
            this.useSemaphore = useSemaphore;
        }

        @Override
        public void run() {
            // 此方法写信息到操控台表明任务已经开始执行了
            useSemaphore.printJob(new Object());
            // 此方法写信息到操控台表明它已经结束运行了。
        }
    }
}
