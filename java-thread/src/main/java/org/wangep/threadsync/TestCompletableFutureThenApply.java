package org.wangep.threadsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/***
 * created by wange on 2020/6/19 17:08
 * thenApply* 可以连接多个CompletableFuture对象，相当于将一个一个的CompletableFuture串联起来了，
 * 第一个CompletableFuture对象的结果会传递到下一个对象中，
 * 并且下一个CompletableFuture对象的结算结果会作为上一个对象的CompletableFuture结果，依次类推，也就是说会改变原始CompletableFuture对象的结果。
 * 注：它和 handle 方法有点类似，都会拿到上一个CompletableFuture对象的结果进行计算，但是区别就是thenApply 会改变原始对象的计算结果，而 handle* 并不会**。
 *
 *
 */
public class TestCompletableFutureThenApply {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<String> f = future.thenApply(i -> i * 10).thenApply(Object::toString);
        System.out.println(future.get());
        System.out.println(f.get());
    }

}
