package com.lohasle.baseframe.s4m3.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by fule on 14-5-21.
 *
 * excel  多sheet 工作表
 */
public class JxlsMutilSheetData {

    /**
     * sheet 里面的数据对象集合 不能为空
     */
    private List objects;
    /**
     * sheet 名称集合
     */
    private List newSheetNames;
    /**
     * 每一个sheet 里面对数据对象的别名，通过这个名称访问 不能为空
     */
    private String beanName;
    /**
     * 每个sheeet都共用的数据  可以为空
     */
    private Map beanParams;
    /**
     * 开始创建的sheet index
     * 注意：不能覆盖模板中已有的sheetName 不能为空
     */
    private int startSheetNum;


    public List getObjects() {
        return objects;
    }

    public void setObjects(List objects) {
        this.objects = objects;
    }

    public List getNewSheetNames() {
        return newSheetNames;
    }

    public void setNewSheetNames(List newSheetNames) {
        this.newSheetNames = newSheetNames;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Map getBeanParams() {
        return beanParams;
    }

    public void setBeanParams(Map beanParams) {
        this.beanParams = beanParams;
    }

    public int getStartSheetNum() {
        return startSheetNum;
    }

    public void setStartSheetNum(int startSheetNum) {
        this.startSheetNum = startSheetNum;
    }

    public JxlsMutilSheetData(List objects, List newSheetNames, String beanName, Map beanParams, int startSheetNum) {
        this.objects = objects;
        this.newSheetNames = newSheetNames;
        this.beanName = beanName;
        this.beanParams = beanParams;
        this.startSheetNum = startSheetNum;
    }

    public JxlsMutilSheetData(int startSheetNum, String beanName, List newSheetNames, List objects) {
        this.startSheetNum = startSheetNum;
        this.beanName = beanName;
        this.newSheetNames = newSheetNames;
        this.objects = objects;
    }

    public JxlsMutilSheetData() {
    }
}
