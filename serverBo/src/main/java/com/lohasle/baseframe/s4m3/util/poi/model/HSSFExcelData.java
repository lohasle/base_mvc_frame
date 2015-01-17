package com.lohasle.baseframe.s4m3.util.poi.model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.IOException;

/**
 * @author: fule
 * @version: Revision 1.0.0
 * @see:
 * @创建日期:14-3-26
 * @功能说明: 基础的exceldata  sheet 数据 对象
 * <p/>
 * 定义了表头和内容的格式
 */
public abstract class HSSFExcelData extends HSSFCommon {

    /**
     * 工作表名称
     */
    private String sheetName;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }


    /**
     * body的单元格 格式
     */
    private HSSFCellStyle bodyCellStyle;


    /**
     * 表头的单元格格式
     */
    private HSSFCellStyle headCellStyle;

    /**
     * 是否冻结表头
     */
    private boolean enableFreezeHead;

    public boolean isEnableFreezeHead() {
        return enableFreezeHead;
    }

    public void setEnableFreezeHead(boolean enableFreezeHead) {
        this.enableFreezeHead = enableFreezeHead;
    }

    public HSSFCellStyle getBodyCellStyle() {
        return bodyCellStyle;
    }

    public void setBodyCellStyle(HSSFCellStyle bodyCellStyle) {
        this.bodyCellStyle = bodyCellStyle;
    }

    public HSSFCellStyle getHeadCellStyle() {
        return headCellStyle;
    }

    public void setHeadCellStyle(HSSFCellStyle headCellStyle) {
        this.headCellStyle = headCellStyle;
    }


    /**
     * 功能：创建HSSFRow 行
     *
     * @param sheet  HSSFSheet
     * @param rowNum int
     * @param height int
     * @return HSSFRow
     */
    public static HSSFRow createRow(HSSFSheet sheet, int rowNum, int height) {
        HSSFRow row = sheet.createRow(rowNum);
        row.setHeight((short) height);
        return row;
    }

    /**
     * 功能：创建CELL 单元格
     *
     * @param row     行号
     *                HSSFRow
     * @param cellNum 单元格号（列号）
     *                int
     * @param style   单元格样式
     *                HSSFStyle
     * @return HSSFCell
     */
    public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 书写 sheet data
     *
     * @param sheet
     * @param excelData
     * @throws java.io.IOException
     */
    public abstract void writeSheetData(HSSFSheet sheet, HSSFExcelData excelData) throws IOException;


}
