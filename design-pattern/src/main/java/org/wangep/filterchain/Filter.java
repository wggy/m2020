package org.wangep.filterchain;

/***
 * created by wange on 2020/9/7 10:54
 */
public interface Filter {

    void doFilter(Request request, Response response, FilterChain filterChain);

}
