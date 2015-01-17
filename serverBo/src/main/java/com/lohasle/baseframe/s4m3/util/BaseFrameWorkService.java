package com.lohasle.baseframe.s4m3.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * 消息读取
 * Created with IntelliJ IDEA.
 * User: fule
 * Date: 2014-05-30
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */

public class BaseFrameWorkService implements MessageSourceAware {

    private MessageSourceAccessor message;

    public void setMessageSource(MessageSource messageSource) {
        this.message = new MessageSourceAccessor(messageSource);
    }

    public MessageSourceAccessor getMessage() {
        return message;
    }


}
