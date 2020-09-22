package org.wangep.filterchain;

/***
 * created by wange on 2020/9/7 17:49
 */
public class HtmlFilter implements Filter {


    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        String newRequestStr = request.getRequestStr().replace(":)", "^-^") + "----HtmlFilter";
        request.setRequestStr(newRequestStr);
        filterChain.doFilter(request, response, filterChain);
        String newResponseStr = response.getResponseStr() + "----HtmlFilter";
        response.setResponseStr(newResponseStr);
    }
}
