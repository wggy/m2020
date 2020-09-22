package org.wangep.filterchain;

/***
 * created by wange on 2020/9/7 17:56
 */
public class TextFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        String newRequestStr = request.getRequestStr().replace("中国10.1", "中国") + "----TextFilter";
        request.setRequestStr(newRequestStr);
        filterChain.doFilter(request, response, filterChain);
        String newResponseStr = response.getResponseStr() + "----TextFilter";
        response.setResponseStr(newResponseStr);
    }
}
