package com.lohasle.baseframe.s4m3.pojo;

import com.lohasle.baseframe.s4m3.pojo.interf.PageBeanAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fule on 14-6-5.
 * 基础pagebean
 */
@Component("pageBean")
public class PageBean implements PageBeanAdapter {
    public PageBean() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    private int pageSize, pageNum, total;
    private List rows;
    private String state, remark;

    /**
     * 建立一个pagebean
     * @param pageNum
     * @param pageSize
     * @param total
     * @param rows
     * @return
     */
    public PageBean instanceNew(int pageNum, int pageSize, int total, List rows){
        PageBean pb = new PageBean();
        pb.setPageNum(pageNum);
        pb.setPageSize(pageSize);
        pb.setTotal(total);
        pb.setRows(rows);
        return pb;
    }

    /**
     * 开始记录
     * @param pageNum
     * @param pageSize
     * @return
     */
    public int getPageStart(int pageNum, int pageSize){
        return   (pageNum - 1) * pageSize;
    }

}
