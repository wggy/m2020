package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/***
 * created by wange on 2020/6/19 18:30
 */
public class TestCompletableFutureThenRun {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);
        CompletableFuture<Void> future2 = future1.thenRun(() -> System.out.println("future1跑完了"));
        System.out.println("doLast, future1 result = " + future1.get());
        System.out.println("doLast, future2 result = " + future2.get());
    }
}
