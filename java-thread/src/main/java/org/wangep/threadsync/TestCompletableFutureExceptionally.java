package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/6/19 16:11
 */
public class TestCompletableFutureExceptionally {
    static int throwException() {
        System.out.println("Start to throw Exception");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Threw !!!");
        int r1 = 10, r2 = 0;
        return r1 / r2;
    }

    static void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFutureExceptionally::throwException);
        CompletableFuture<Integer> future2 = future1.whenCompleteAsync((result, exception) -> {
            System.out.println("doWhenComplete, result = " + result);
            System.out.println("doWhenComplete, exception = " + (exception == null ? "无异常" : exception.getClass()));
        });

        System.out.println("doLast, future1 result = " + future1.get());
    }

    static void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFutureExceptionally::throwException);
        CompletableFuture<Integer> future2 = future1.whenCompleteAsync((result, exception) -> {
            System.out.println("doWhenComplete, result = " + result);
            System.out.println("doWhenComplete, exception = " + (exception == null ? "无异常" : exception.getClass()));
        });

        CompletableFuture<Integer> future3 = future2.exceptionally(exception -> {
            System.out.println("计算执行过程中发生了异常，exception:" + exception.getClass());
            return 0;
        });


        System.out.println("doLast, future1 result = " + future1.get());
        System.out.println("doLast, future2 result = " + future2.get());
        System.out.println("doLast, future3 result = " + future3.get());
    }

    static void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFutureExceptionally::throwException);
        CompletableFuture<Integer> future2 = future1.whenCompleteAsync((result, exception) -> {
            System.out.println("doWhenComplete, result = " + result);
            System.out.println("doWhenComplete, exception = " + (exception == null ? "无异常" : exception.getClass()));
        });
        CompletableFuture<Integer> future3 = future2.exceptionally(exception -> {
            System.out.println("计算执行过程中发生了异常，exception:" + exception.getClass());
            return 0;
        });


//        System.out.println("doLast, future1 result = " + future1.get());
//        System.out.println("doLast, future2 result = " + future2.get());
        System.out.println("doLast, future3 result = " + future3.get());
    }

    static void test4() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);
        CompletableFuture<Integer> future2 = future1.whenCompleteAsync((result, exception) -> {
            System.out.println("doWhenComplete, result = " + result);
            System.out.println("doWhenComplete, exception = " + (exception == null ? "无异常" : exception.getClass()));
        });
        CompletableFuture<Integer> future3 = future2.exceptionally(exception -> {
            System.out.println("计算执行过程中发生了异常，exception:" + exception.getClass());
            return 0;
        });


        System.out.println("doLast, future1 result = " + future1.get());
        System.out.println("doLast, future2 result = " + future2.get());
        System.out.println("doLast, future3 result = " + future3.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test4();
    }


}
