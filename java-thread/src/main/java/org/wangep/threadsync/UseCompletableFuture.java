package org.wangep.threadsync;

import java.util.Random;
import java.util.concurrent.*;

/***
 * created by wange on 2020/6/17 18:33
 */
public class UseCompletableFuture {

    public static void main(String[] args) {

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });


        System.out.println(completableFuture.join());
        System.out.println("------------------------");
        CompletableFuture<Integer> newFuture = new CompletableFuture<>();

        new Client("Client1", newFuture).start();
        new Client("Client2", newFuture).start();
        System.out.println("waiting");
        newFuture.complete(100);


        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(UseCompletableFuture::getMoreData);
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });

        try {
            System.out.println(f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CompletableFuture.completedFuture(100);
    }

    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();
    static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(1000);
    }

    static class Client extends Thread {
        CompletableFuture<Integer> f;
        Client(String threadName, CompletableFuture<Integer> f) {
            super(threadName);
            this.f = f;
        }
        @Override
        public void run() {
            try {
                System.out.println(this.getName() + ": " + f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
