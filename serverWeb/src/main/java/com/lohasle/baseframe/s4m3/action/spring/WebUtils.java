package com.lohasle.baseframe.s4m3.action.spring;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fule
 * @version: Revision 1.0.0
 * @see:
 * @创建日期:14-1-18
 * @功能说明: web 工具类
 */
public class WebUtils {

    /**
     * 取得当前request
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }


}
