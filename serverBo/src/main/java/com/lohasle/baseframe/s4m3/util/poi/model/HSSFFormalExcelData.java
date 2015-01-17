package com.lohasle.baseframe.s4m3.util.poi.model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.util.Date;

/**
 * @author: fule
 * @version: Revision 1.0.0
 * @see:
 * @创建日期:14-3-26
 * @功能说明: 一个普通的excel 表数据 可带有合并等操作
 */
public class HSSFFormalExcelData extends HSSFExcelData {

    /**
     * 表头区
     */
    private String[][] head;

    /**
     * 二维表数据区
     */
    private BodyCell[][] body;


    /**
     * 数据标准列长度
     */
    private int dataColLen;


    public String[][] getHead() {
        return head;
    }

    public void setHead(String[][] head) {
        this.head = head;
    }

    public BodyCell[][] getBody() {
        return body;
    }

    public void setBody(BodyCell[][] body) {
        this.body = body;
    }

    public int getDataColLen() {
        return dataColLen;
    }

    public void setDataColLen(int dataColLen) {
        this.dataColLen = dataColLen;
    }

    public HSSFFormalExcelData() {
    }

    ;

    public HSSFFormalExcelData(String sheetName, int dataColLen, String[][] head, BodyCell[][] body) {
        super();
        super.setSheetName(sheetName);
        this.dataColLen = dataColLen;
        this.head = head;
        this.body = body;
    }

    public HSSFFormalExcelData(String sheetName, int dataColLen, String[][] head, BodyCell[][] body, boolean enableFreezeHead) {
        super();
        super.setSheetName(sheetName);
        super.setEnableFreezeHead(enableFreezeHead);
        this.dataColLen = dataColLen;
        this.head = head;
        this.body = body;
    }

    /**
     * 设置合并单元格的边框样式
     *
     * @param sheet HSSFSheet
     * @param ca    CellRangAddress
     * @param style CellStyle
     */
    public void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca, CellStyle style) {
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
            HSSFRow row = HSSFCellUtil.getRow(i, sheet);
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
                HSSFCell cell = HSSFCellUtil.getCell(row, j);
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * 书写 sheet data
     * <p/>
     * 这里的数据支持合并等
     *
     * @param sheet
     * @param excelData
     * @throws java.io.IOException
     */

    public void writeSheetData(HSSFSheet sheet, HSSFExcelData excelData) throws IOException {
        HSSFCellStyle headStyle = excelData.getHeadCellStyle();
        if (headStyle == null) {
            headStyle = excelData.getBodyCellStyle();
        }

        HSSFFormalExcelData formalExcelData = (HSSFFormalExcelData) excelData;
        String[][] head = formalExcelData.getHead();
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

        BodyCell[][] body = formalExcelData.getBody();
        if (body != null && body.length > 0) {
            // 循环插入数据
            for (int j = 0; j < body.length; j++) {
                int indexRowNo = (short) j + head.length; //内容区  书写行数据
                HSSFRow row = sheet.createRow(indexRowNo);
                for (int m = 0; m < dataColLen; m++) { // dataColLen 表头长度
                    // 创建单元格，并设置值
                    HSSFCell cell = createCell(row, m, excelData.getBodyCellStyle());
                    BodyCell bodyCell = body[j][m];
                    if (bodyCell != null) {
                        Object value = bodyCell.getValue();
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
                            style.setBorderBottom(HSSFCellStyle.BORDER_DASHED); //下边框
                            style.setBorderLeft(HSSFCellStyle.BORDER_DASHED);//左边框
                            style.setBorderTop(HSSFCellStyle.BORDER_DASHED);//上边框
                            style.setBorderRight(HSSFCellStyle.BORDER_DASHED);//右边框
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

                        // 执行合并
                        if (bodyCell.getRowspan() > 0) {
                            //执行行合并
                            mergeCell(sheet, indexRowNo, (indexRowNo + bodyCell.getRowspan()-1), m, m);
                        }
                        if (bodyCell.getColspan() > 0) {
                            //执行列合并
                            mergeCell(sheet, indexRowNo, indexRowNo, m, (m + bodyCell.getColspan()-1)); //从0开始的 减1
                        }
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
        if (head != null && excelData.isEnableFreezeHead()) {
            sheet.createFreezePane(0, head.length, 0, head.length); //冻结表头
        }
    }


    public class BodyCell {

        private Object value;
        private int rowspan;//行合并大小
        private int colspan;//列合并大小

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public int getRowspan() {
            return rowspan;
        }

        public void setRowspan(int rowspan) {
            this.rowspan = rowspan;
        }

        public int getColspan() {
            return colspan;
        }

        public void setColspan(int colspan) {
            this.colspan = colspan;
        }

        public BodyCell() {
        }


        public BodyCell(Object value, int rowspan, int colspan) {
            this.value = value;
            this.rowspan = rowspan;
            this.colspan = colspan;
        }
    }

}
