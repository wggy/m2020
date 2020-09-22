package org.wangep.filterchain;

/***
 * created by wange on 2020/9/7 17:57
 */
public class Client {
    public static void main(String[] args) {
        String requestStr = "1111111,2222222:),中国10.1国庆节....";
        Request request = new Request();
        request.setRequestStr(requestStr);
        Response response = new Response();

        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new HtmlFilter())
                .addFilter(new TextFilter())
                .doFilter(request, response, filterChain);


        System.out.println(request.getRequestStr());
        System.out.println(response.getResponseStr());
    }
}
