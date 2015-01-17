package com.lohasle.baseframe.s4m3.util.poi.model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.IOException;
import java.util.Date;

/**
 * @author: fule 简单excel 数据对象
 * @version: Revision 1.0.0
 * @see:
 * @创建日期:14-3-26
 * @功能说明: 用于描述简单二维表格
 */
public class HSSFSimpleExcelData extends HSSFExcelData {

    public HSSFSimpleExcelData() {

    }


    public HSSFSimpleExcelData(String sheetName, int dataColLen, String[][] head, Object[][] body) {
        super();
        super.setSheetName(sheetName);
        this.dataColLen = dataColLen;
        this.head = head;
        this.body = body;
    }
    public HSSFSimpleExcelData(String sheetName, int dataColLen, String[][] head, Object[][] body,boolean enableFreezeHead) {
        super();
        super.setSheetName(sheetName);
        super.setEnableFreezeHead(enableFreezeHead);
        this.dataColLen = dataColLen;
        this.head = head;
        this.body = body;
    }


    /**
     * 表头区
     */
    private String[][] head;

    /**
     * 二维表数据区
     */
    private Object[][] body;


    /**
     * 数据标准列长度
     */
    private int dataColLen;


    public int getDataColLen() {
        return dataColLen;
    }

    public void setDataColLen(int dataColLen) {
        this.dataColLen = dataColLen;
    }

    /**
     * @return head属性
     */
    public String[][] getHead() {
        return head;
    }

    /**
     * @param head 设置head属性
     */
    public void setHead(String[][] head) {
        this.head = head;
    }

    /**
     * @return body属性
     */
    public Object[][] getBody() {
        return body;
    }

    /**
     * @param body 设置body属性
     */
    public void setBody(Object[][] body) {
        this.body = body;
    }


    /**
     * 书写简单的excel  二维表
     *
     * @param sheet     工作区
     * @param excelData 数据
     * @throws java.io.IOException
     * @功能说明:书写sheet工作表 .
     * @author fule
     * 2013-10-22 fule
     */
    public void writeSheetData(HSSFSheet sheet, HSSFExcelData excelData) throws IOException {
        HSSFCellStyle headStyle = excelData.getHeadCellStyle();
        if (headStyle == null) {
            headStyle = excelData.getBodyCellStyle();
        }
        //简单 excel
        HSSFSimpleExcelData simpleExcelData = (HSSFSimpleExcelData) excelData;
        String[][] head = simpleExcelData.getHead();
        if (head != null) {
            for (int i = 0, len = head.length; i < len; i++) {
                // 创建表头行
                HSSFRow row = createRow(sheet, 0, 550);
                // 表头列
                for (int j = 0; j < head[i].length; j++) {
                    HSSFCell cell = createCell(row, j, headStyle);
                    cell.setCellValue(head[i][j].toString());
                }
            }
        }
        Object[][] body = simpleExcelData.getBody();
        if (body != null && body.length > 0) {
            // 循环插入数据
            for (int j = 0; j < body.length; j++) {
                HSSFRow row = sheet.createRow((short) j + head.length);
                for (int m = 0; m < dataColLen; m++) {
                    // 创建单元格，并设置值
                    HSSFCell cell = createCell(row, m, excelData.getBodyCellStyle());
                    Object value = body[j][m];
                    if (value instanceof Double || value instanceof Float
                            || value instanceof Integer) {
                        cell.setCellValue(Double.parseDouble(String.valueOf(value)));
                    } else if (value instanceof Date) {// 需要格式化data 这里进行
                        cell.setCellValue((Date) value);
                    } else if (value instanceof Boolean) {
                        cell.setCellValue((Boolean) value);
                    } else if (value == null) {
                        cell.setCellValue("");
                    } else if (value instanceof HSSFFormula) {//公式列
                        HSSFFormula hf = (HSSFFormula) value;
                        HSSFCellStyle style = hf.createFormulaCellStyle(sheet, hf);
                        cell.setCellStyle(style);
                        String hfStr = hf.getFormula();
                        if (hfStr != null && !"".equals(hfStr) && !"null".equals(hfStr.trim())) {
                            cell.setCellFormula(hfStr);
                        } else {
                            cell.setCellValue(hf.getRemark());
                        }
                    } else {
                        cell.setCellValue(String.valueOf(value));
                    }
                }
            }

            if (dataColLen > 0) {
                for (int i = 0; i < dataColLen; i++) {
                    sheet.autoSizeColumn(i);// 自动调整列宽
                }
            }

        }


        sheet.setForceFormulaRecalculation(true);//刷新有公式的行

        /**
         * createFreezePane(a,b,c,d)
         * ａ表示要冻结的列数；
         * ｂ表示要冻结的行数；
         * ｃ表示右边区域[可见]的首列序号； 列
         * ｄ表示下边区域[可见]的首行序号； 行
         */
        if (head != null&&excelData.isEnableFreezeHead()) {
            sheet.createFreezePane(0, head.length, 0, head.length); //冻结表头
        }
    }
}
