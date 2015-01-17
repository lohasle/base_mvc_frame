package com.lohasle.baseframe.s4m3.util.poi.model;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author: fule
 * @version: Revision 1.0.0
 * @see: HSSF 基础类
 * @创建日期:14-3-26
 * @功能说明:
 */
public  class HSSFCommon {
    /**
     * @param wb         HSSFWorkbook对象
     * @param fontName   字体名称
     * @param boldweight 字体宽度
     * @param color      字体颜色
     * @param size       字体大小
     * @return
     * @功能说明:功能：创建字体 .
     * @author fule
     * 2013-10-22 fule
     */
    public Font createFont(HSSFWorkbook wb, String fontName, short boldweight, short color,
                           short size) {
        Font font = wb.createFont();
        font.setBoldweight(boldweight);
        font.setFontName(fontName);
        font.setColor(color);
        font.setFontHeight(size);
        return font;
    }


    /**
     * 创建默认的body default style
     * @param wb
     * @return
     */
    public HSSFCellStyle getBodyDefaultStyle(HSSFWorkbook wb){
        HSSFCellStyle defaultCellStyle = wb.createCellStyle();
        defaultCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        defaultCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        defaultCellStyle.setFont(createFont(wb, "宋体", HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
                (short) 230));
        defaultCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        defaultCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        defaultCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        defaultCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        return defaultCellStyle;
    }


    /**
     * 创建默认的head default style
     * @param wb
     * @return
     */
    public HSSFCellStyle getHeadDefaultStyle(HSSFWorkbook wb){
        HSSFCellStyle headCellStyle = wb.createCellStyle();

        //高亮
        headCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        headCellStyle.setFont(createFont(wb,"宋体", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.WHITE.index,
                (short) 230));

        headCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        return headCellStyle;
    }


    /**
     * 合并 单元格
     * @param sheet
     * @param firstRow 开始合并的行
     * @param lastRow  结束合并的行
     * @param firstCol 开始合并的列
     * @param lastCol  结束合并的列
     */
    public  void mergeCell(HSSFSheet sheet,int firstRow, int lastRow, int firstCol, int lastCol){
        sheet.addMergedRegion(new CellRangeAddress(
                firstRow, //first row (0-based)
                lastRow, //last row  (0-based)
                firstCol, //first column (0-based)
                lastCol  //last column  (0-based)
        ));
    }

}
