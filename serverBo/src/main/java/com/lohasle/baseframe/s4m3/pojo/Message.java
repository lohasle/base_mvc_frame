package com.lohasle.baseframe.s4m3.pojo;


import com.lohasle.baseframe.s4m3.pojo.interf.MessageAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 兼容老系统的消息适配实体
 */
@Component("hjmsg")
public class Message implements MessageAdapter {

    public Message() {
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getDetail() {
        return detail;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String stateCode = STATE_SUCCESS, detail;
    private Long workFlowId;
    private PageBean pageBean;
    private Object object;
    private List list;
    public static final String STATE_SUCCESS = "1";
    public static final String STATE_ERROR = "0";
    public static final String MESSAGE_SUCCESS_DETAIL = "ok";


    /**
     * 默认为成功
     * @param object
     * @return
     */
    public Message defaultNew(Object object){
        return new Message(MESSAGE_SUCCESS_DETAIL,object);
    }

    /**
     * 默认为成功
     * @param object
     * @return
     */
    public Message defaultNew(Object object,String detail){
        return new Message(MESSAGE_SUCCESS_DETAIL,detail,object);
    }

    /**
     * 默认为成功
     * @return
     */
    public Message instanceNew( String detail, Object object){
        return new Message(detail,object);
    }
    /**
     * @return
     */
    public Message instanceNew(String stateCode, String detail, Object object){
        return new Message(stateCode,detail,object);
    }

    /**
     * @return
     */
    public Message instanceNew(String stateCode, String detail, PageBean pageBean){
        return new Message(stateCode,detail,pageBean);
    }

    public  Message instanceNew(){
        return new Message();
    }

    public String getStrMsg() {
        return this.getDetail();
    }

    public void setStrMsg(String strMsg) {
        this.setDetail(strMsg);
    }

    public Object getObjMsg() {
        return this.getObject();
    }

    public void setObjMsg(Object obj) {
        this.setObject(obj);
    }

    /**
     * @return
     */
    public Message instanceNew(String detail, PageBean pageBean){
        return new Message(detail,pageBean);
    }

    public Message instanceNew(String stateCode, String detail) {
        return new Message(detail,pageBean);
    }

    public Message(String stateCode, String detail) {
        this.stateCode = stateCode;
        this.detail = detail;
    }

    public Message(String stateCode, PageBean pageBean) {
        this.stateCode = stateCode;
        this.pageBean = pageBean;
    }

    public Message(String stateCode, String detail, Object object) {
        this.stateCode = stateCode;
        this.detail = detail;
        this.object = object;
    }

    public Message(List list, String stateCode) {
        this.list = list;
        this.stateCode = stateCode;
    }

    public Message(String stateCode, Object object) {
        this.stateCode = stateCode;
        this.object = object;
    }

    public Message(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}
