package com.lohasle.baseframe.s4m3.services.basic;

import com.lohasle.baseframe.s4m3.pojo.interf.MessageAdapter;
import com.lohasle.baseframe.s4m3.pojo.interf.PageBeanAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by fule on 14-5-30.
 */

/**
 *   基础服务  用于查找系统配置 用户信息 消息读取
 */
public class BasicServiceImpl  implements BasicService,MessageSourceAware {


    /**
     * 兼容系统的pagebean
     */
    @Autowired
    private PageBeanAdapter pageBean;

    /**
     * 兼容系统的hjmsg（message）
     */
    @Autowired
    private MessageAdapter hjmsg;

    /**
     * Spring 消息读取
     */
    private MessageSourceAccessor message;

    public void setMessageSource(MessageSource messageSource) {
        this.message = new MessageSourceAccessor(messageSource);
    }

    public MessageSourceAccessor getMessage() {
        return message;
    }

    public PageBeanAdapter getPageBeanAdapter() {
        return pageBean;
    }

    public MessageAdapter getMessageAdapter() {
        return hjmsg;
    }


}
