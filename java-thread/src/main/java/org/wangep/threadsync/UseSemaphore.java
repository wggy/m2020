package org.wangep.threadsync;


import java.util.concurrent.Semaphore;

/***
 * created by wange on 2020/4/9 17:42
 */
public class UseSemaphore {
    public static void main(String[] args) {
        UseSemaphore useSemaphore = new UseSemaphore();
        //创建10个threads。每个线程会执行一个发送文档到print queue的Job对象。
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(useSemaphore), "Thread" + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }

    private final Semaphore semaphore;

    public UseSemaphore() {
        this.semaphore = new Semaphore(1);
    }

    public void printJob(Object document) {
        try {
            // 你必须调用acquire()方法获得semaphore。这个方法会抛出 InterruptedException异常，使用必须包含处理这个异常的代码。
            semaphore.acquire();
            System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());
            //然后，实现能随机等待一段时间的模拟打印文档的行。
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(), duration);
            Thread.sleep(duration);
            //最后，释放semaphore通过调用semaphore的relaser()方法。
            System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    static class Job implements Runnable {

        private UseSemaphore useSemaphore;

        public Job(UseSemaphore useSemaphoreSyncArrayResource) {
            this.useSemaphore = useSemaphoreSyncArrayResource;
        }

        @Override
        public void run() {
            // 此方法写信息到操控台表明任务已经开始执行了。

            useSemaphore.printJob(new Object());
            // 此方法写信息到操控台表明它已经结束运行了。


        }
    }

}
