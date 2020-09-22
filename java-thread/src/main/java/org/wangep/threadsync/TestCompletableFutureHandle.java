package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/***
 * created by wange on 2020/6/19 17:05
 *
 * 它返回的新CompletableFuture对象的计算结果是handle*方法的返回值，并不是原始计算逻辑的返回值
 *
 */
public class TestCompletableFutureHandle {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);
        CompletableFuture<Integer> future2 = future1.handleAsync((result, exception) -> {
            System.out.println("计算已执行完毕，result:" + result);
            System.out.println("计算已执行完毕，exception:" + (exception == null ? "无异常" : exception.getClass()));
            return result + 1;
        });

        System.out.println("执行到最后一段代码了，future1 result：" + future1.get());
        System.out.println("执行到最后一段代码了，future2 result：" + future2.get());
    }
}
