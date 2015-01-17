package com.lohasle.baseframe.s4m3.pojo.interf;

/**
 * Created by fule on 14-6-12.
 */

import com.lohasle.baseframe.s4m3.pojo.PageBean;

/**
 * 兼容老系统的消息适配器
 */
public interface MessageAdapter {

    /**
     * 初始化一个默认的消息
     * @param object
     * @return
     */
    MessageAdapter defaultNew(Object object);

    MessageAdapter defaultNew(Object object,String detail);

    /**
     * 根据不同属性 创建消息
     * @param detail
     * @param object
     * @return
     */
    MessageAdapter instanceNew(String detail, Object object);

    MessageAdapter instanceNew(String detail, PageBean pageBean);

    MessageAdapter instanceNew(String stateCode, String detail);

    MessageAdapter instanceNew(String stateCode, String detail, Object object);

    MessageAdapter instanceNew(String stateCode, String detail, PageBean pageBean);


    String getStrMsg();

    void setStrMsg(String msg);

    Object getObjMsg();

    void setObjMsg(Object obj);



}
