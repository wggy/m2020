package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/7/15 16:02
 */
public class TestCompletableFutureRunAfterBoth {

    public static void main(String[] args) {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("future1 in !");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("future1 game over !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("future2 in !");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("future2 game over !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            future1.runAfterBoth(future2, () -> System.out.println("all over...")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(111);
    }

}
