package com.lohasle.baseframe.s4m3.action.spring.Bean;

import org.springframework.stereotype.Component;

/**
 * Created by fule on 14-6-10.
 */
public class JqDataRequestBean implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    //当前页码
    private int page;
    //页面可显示行数 页码大小
    private int rows;
    //用于排序的列名
    private String sidx;
    //排序的方式desc/asc
    private String sord;
    //是否是搜索请求
    private boolean search;
    //已经发送的请求的次数
    private String nd;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    /**
     * 拼接排序字段和排序方式
     * @return
     */
    public String getOrderStr(){
        return this.getSidx()+" "+this.getSord();
    }
}
