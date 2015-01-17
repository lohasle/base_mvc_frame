package com.lohasle.baseframe.s4m3.constant.exception;

/**
 * Created with IntelliJ IDEA.
 * User: fule
 * Date: 13-6-8
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public class ServiceException extends RuntimeException {
    private String msg;
    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        msg = message;
    }

    @Override
    public String getMessage() {
        return msg == null || "".equals(msg) ? "系统服务异常"
                : msg;
    }
}
