package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/6/19 17:54
 *
 * 它和 thenAccept 一样，都是纯消费，但是thenAccept*只能消费一个CompletableFuture对象，
 * 而thenAcceptBoth* 能在两个不同的CompletableFuture对象执行完成后，消费他们两个的计算结果。
 */
public class TestCompletableFutureThenAcceptBoth {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);

        future1.thenAcceptBoth(future2, (x, y) -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("future1, future2 finished, result = " + (x + y));
        });

        System.out.println("doLast, future1 result = " + future1.get());
        System.out.println("doLast, future2 result = " + future2.get());
    }
}
