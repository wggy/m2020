package org.wangep.deferredresult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.servlet.http.HttpServletRequest;

/***
 * created by wange on 2020/3/31 16:36
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_MODIFIED)//返回304状态码
    @ExceptionHandler(AsyncRequestTimeoutException.class) //捕获特定异常
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpServletRequest request) {
        System.out.println("handleAsyncRequestTimeoutException");
    }

}
