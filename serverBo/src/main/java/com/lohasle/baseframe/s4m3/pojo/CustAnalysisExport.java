package com.lohasle.baseframe.s4m3.pojo;


/**
 * 客户流量报表导出数据POJO
 * @author 付乐
 * @createTime 2013-7-19
 */
public class CustAnalysisExport {
    @Override
    public String toString() {
        return "CustAnalysisExport [staffName=" + staffName + ", todayCustVisitSum="
            + todayCustVisitSum + ", newCustSpecialSum=" + newCustSpecialSum + ", noRegisterNum="
            + noRegisterNum + ", newCustSum=" + newCustSum + ", custSum=" + custSum
            + ", oldCustSum=" + oldCustSum + ", meetPhoneSum=" + meetPhoneSum + ", reMeetPhoneSum="
            + reMeetPhoneSum + "]";
    }

    /**
     * 销售人员名称
     */
    private String staffName;
    /**
     * 今日客户到访总量
     */
    private Long todayCustVisitSum;
    /**
     * 新客户专访量
     */
    private Long newCustSpecialSum;
    /**
     * 路过量（自己填，填完增加今日和总客户量）
     */
    private Long noRegisterNum;
    /**
     * 新客户到访总量
     */
    private Long newCustSum;
    /**
     * 客户总量
     */
    private Long custSum;
    /**
     * 老客户到访总量
     */
    private Long oldCustSum;
    /**
     * 接听电话组数 （自己填，填完增加今日和总客户量）
     */
    private Long meetPhoneSum;
    /**
     * 电话回访数量
     */
    private Long reMeetPhoneSum;
    public String getStaffName() {
        return staffName;
    }
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    public Long getTodayCustVisitSum() {
        return todayCustVisitSum;
    }
    public void setTodayCustVisitSum(Long todayCustVisitSum) {
        this.todayCustVisitSum = todayCustVisitSum;
    }
    public Long getNewCustSpecialSum() {
        return newCustSpecialSum;
    }
    public void setNewCustSpecialSum(Long newCustSpecialSum) {
        this.newCustSpecialSum = newCustSpecialSum;
    }
    public Long getNoRegisterNum() {
        return noRegisterNum;
    }
    public void setNoRegisterNum(Long noRegisterNum) {
        this.noRegisterNum = noRegisterNum;
    }
    public Long getNewCustSum() {
        return newCustSum;
    }
    public void setNewCustSum(Long newCustSum) {
        this.newCustSum = newCustSum;
    }
    public Long getCustSum() {
        return custSum;
    }
    public void setCustSum(Long custSum) {
        this.custSum = custSum;
    }
    public Long getOldCustSum() {
        return oldCustSum;
    }
    public void setOldCustSum(Long oldCustSum) {
        this.oldCustSum = oldCustSum;
    }
    public Long getMeetPhoneSum() {
        return meetPhoneSum;
    }
    public void setMeetPhoneSum(Long meetPhoneSum) {
        this.meetPhoneSum = meetPhoneSum;
    }
    public Long getReMeetPhoneSum() {
        return reMeetPhoneSum;
    }
    public void setReMeetPhoneSum(Long reMeetPhoneSum) {
        this.reMeetPhoneSum = reMeetPhoneSum;
    }
    
   

    
}
