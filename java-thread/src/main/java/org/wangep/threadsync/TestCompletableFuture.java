package org.wangep.threadsync;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/6/19 15:54
 */
public class TestCompletableFuture {

    private static final Random random = new Random();
    private static final long time = System.currentTimeMillis();


    public static int getMoreData() {
        System.out.println("Begin to start compute");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish to compute, passed: " + System.currentTimeMillis());
        return random.nextInt(100);
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);

        CompletableFuture<Integer> future2 = future.whenComplete((result, execution) -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("doWhenComplete......., result = " + result);
            System.out.println("doWhenComplete......., exception = " + (execution == null ? "NoException" : execution.getMessage()));
        });

        System.out.println("DoLast, future result = "+ future.get());
        System.out.println("DoLast, future2 result = "+ future2.get());

    }

}
