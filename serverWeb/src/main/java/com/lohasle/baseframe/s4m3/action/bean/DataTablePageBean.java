package com.lohasle.baseframe.s4m3.action.bean;

/**
 * Created by fule on 14-6-7.
 */


import com.lohasle.baseframe.s4m3.pojo.PageBean;

import java.util.List;

/**
 * DataTablePageBean
 */
public class DataTablePageBean {


    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    private int draw;//当前页
    private int recordsTotal;
    private int recordsFiltered;
    private List data;

    public DataTablePageBean instance(int draw,int recordsTotal,int recordsFiltered,List data){
        DataTablePageBean pb = new DataTablePageBean();
        pb.setDraw(draw);
        pb.setRecordsTotal(recordsTotal);
        pb.setRecordsFiltered(recordsFiltered);
        pb.setData(data);
        return pb;
    }

    public DataTablePageBean instanceByPageBean(PageBean pageBean){
        DataTablePageBean pb = new DataTablePageBean();
        pb.setDraw(pageBean.getPageNum());
        pb.setRecordsTotal(pageBean.getTotal());
        pb.setRecordsFiltered(pageBean.getTotal());
        pb.setData(pageBean.getRows());
        return pb;
    }
}
