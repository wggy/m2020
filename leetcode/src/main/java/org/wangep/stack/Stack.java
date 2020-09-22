package org.wangep.stack;

/***
 * created by wange on 2020/9/16 15:31
 */
public interface Stack<T> {
    Boolean isEmpty();

    void push(T data);

    T peek();

    T pop();

    int size();
}
