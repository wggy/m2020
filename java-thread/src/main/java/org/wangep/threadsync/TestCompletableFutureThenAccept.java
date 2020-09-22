package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/***
 * created by wange on 2020/6/19 17:50
 */
public class TestCompletableFutureThenAccept {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);
        CompletableFuture<Void> future2 = future1.thenAccept(ret -> System.out.println("doThenAccept, result = " + ret));

        System.out.println("DoLast, future1 result = " + future1.get());
        System.out.println("DoLast, future2 result = " + future2.get());
    }
}
