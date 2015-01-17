package com.lohasle.baseframe.s4m3.action.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by fule on 14-6-9.
 */
public class JqGridBean {
    private int page;//当前页
    private int total;//总页数
    private int records;//总记录数量
    private List rows;//数据
    //自定义数据
    private Map<String, Object> userdata;

    public List getRows() {
        return rows;
    }

    public Map<String, Object> getUserdata() {
        return userdata;
    }

    public void setUserdata(Map<String, Object> userdata) {
        this.userdata = userdata;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }



}
