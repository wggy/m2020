package org.wangep.filterchain;

import com.google.common.collect.Lists;

import java.util.List;

/***
 * created by wange on 2020/9/7 11:49
 */
public class FilterChain implements Filter {

    private List<Filter> filters = Lists.newArrayList();
    private int currentPosition = 0;

    public FilterChain addFilter(Filter filter) {
        this.filters.add(filter);
        return this;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        if (this.currentPosition == this.filters.size()) {
            return;
        }
        this.currentPosition++;
        Filter nextFilter = this.filters.get(this.currentPosition - 1);
        nextFilter.doFilter(request, response, this);
    }
}
