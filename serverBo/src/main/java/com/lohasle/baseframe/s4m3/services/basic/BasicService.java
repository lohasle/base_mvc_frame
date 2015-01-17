package com.lohasle.baseframe.s4m3.services.basic;

import com.lohasle.baseframe.s4m3.pojo.interf.MessageAdapter;
import com.lohasle.baseframe.s4m3.pojo.interf.PageBeanAdapter;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * Created by fule on 14-5-30.
 */

/**
 * 基础服务  用于查找系统配置 用户信息 等
 */
public interface BasicService {

    /**
     * 设置 消息
     * @param messageSource
     */
    void setMessageSource(MessageSource messageSource);

    /**
     * 获取 消息
     */
    MessageSourceAccessor getMessage();

    /**
     * 获取分页适配器
     * @return
     */
    PageBeanAdapter getPageBeanAdapter();

    /**
     * 获取消息适配器
     * @return
     */
    MessageAdapter getMessageAdapter();



}
