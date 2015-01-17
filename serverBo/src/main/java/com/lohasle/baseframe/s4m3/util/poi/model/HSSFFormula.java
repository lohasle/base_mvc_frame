package com.lohasle.baseframe.s4m3.util.poi.model;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author fule
 * @version Revision 1.0.0
 * @see:
 * @创建日期：2013-11-1
 * @功能说明：
 */

/**
 * excel 的公式信息POJO
 *
 * @author fule
 * @version Revision 1.0.0
 * @see:
 * @创建日期：2013-11-1
 * @功能说明：
 */
public class HSSFFormula extends HSSFCommon {

    /**
     * 公式表达式
     */
    private String formula;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字体名称
     */
    private String fontName;

    /**
     * 字体宽度
     */
    private short fontBoldWeight;
    /**
     * 字体颜色
     */
    private short fontColor;
    /**
     * 字体高度
     */
    private short fontHeight;

    /**
     * 水平位置
     */
    private short align;

    /**
     * 垂直位置
     */
    private short verticalAlign;

    /**
     * 背景颜色
     */
    private short fillForegroundColor;

    /**
     * 背景模式
     */
    private short fillForegroundPattern;


    public HSSFFormula() {
    }


    /**
     * @return formula属性
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula 设置formula属性
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return remark属性
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 设置remark属性
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return fontName属性
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * @param fontName 设置fontName属性
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    /**
     * @return fontBoldWeight属性
     */
    public short getFontBoldWeight() {
        return fontBoldWeight;
    }

    /**
     * @param fontBoldWeight 设置fontBoldWeight属性
     */
    public void setFontBoldWeight(short fontBoldWeight) {
        this.fontBoldWeight = fontBoldWeight;
    }

    /**
     * @return fontColor属性
     */
    public short getFontColor() {
        return fontColor;
    }

    /**
     * @param fontColor 设置fontColor属性
     */
    public void setFontColor(short fontColor) {
        this.fontColor = fontColor;
    }


    /**
     * @return align属性
     */
    public short getAlign() {
        return align;
    }

    /**
     * @param align 设置align属性
     */
    public void setAlign(short align) {
        this.align = align;
    }

    /**
     * @return verticalAlign属性
     */
    public short getVerticalAlign() {
        return verticalAlign;
    }

    /**
     * @param verticalAlign 设置verticalAlign属性
     */
    public void setVerticalAlign(short verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    /**
     * @return fillForegroundColor属性
     */
    public short getFillForegroundColor() {
        return fillForegroundColor;
    }

    /**
     * @param fillForegroundColor 设置fillForegroundColor属性
     */
    public void setFillForegroundColor(short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }

    /**
     * @return fillForegroundPattern属性
     */
    public short getFillForegroundPattern() {
        return fillForegroundPattern;
    }

    /**
     * @param fillForegroundPattern 设置fillForegroundPattern属性
     */
    public void setFillForegroundPattern(short fillForegroundPattern) {
        this.fillForegroundPattern = fillForegroundPattern;
    }

    /**
     * @param fontHeight 设置fontHeight属性
     */
    public void setFontHeight(short fontHeight) {
        this.fontHeight = fontHeight;
    }

    /**
     * @return fontHeight属性
     */
    public short getFontHeight() {
        return fontHeight;
    }

    /**
     * @return
     * @功能说明:构造简单公式样式 .
     * @author fule
     * 2013-11-1 fule
     */
    public static HSSFFormula createSimpleStyleHSSFFormula() {
        HSSFFormula hf = new HSSFFormula();
        hf.setFontName("宋体");
        hf.setFontBoldWeight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
        hf.setFontColor(HSSFFont.COLOR_NORMAL);
        hf.setFontHeight((short) 230);
        hf.setAlign(HSSFCellStyle.ALIGN_CENTER);//水平居中
        hf.setVerticalAlign(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        hf.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//背景色
        hf.setFillForegroundPattern(CellStyle.SOLID_FOREGROUND);//背景模式
        return hf;
    }


    /**
     * @param sheet
     * @param hf
     * @return
     * @功能说明:创建公式样式 .
     * @author fule
     * 2013-11-1 fule
     */
    public HSSFCellStyle createFormulaCellStyle(HSSFSheet sheet, HSSFFormula hf) {
        HSSFWorkbook wb = sheet.getWorkbook();
        HSSFCellStyle cellStyle1 = wb.createCellStyle();//标记公式的那一列
        Font font = createFont(wb, hf.getFontName(), hf.getFontBoldWeight(), hf.getFontColor(),
                hf.getFontHeight());
        cellStyle1.setAlignment(hf.getAlign()); // 指定单元格居中对齐
        cellStyle1.setVerticalAlignment(hf.getVerticalAlign()); // 垂直居中
        cellStyle1.setFont(font);
        cellStyle1.setFillForegroundColor(hf.getFillForegroundColor());
        cellStyle1.setFillPattern(hf.getFillForegroundPattern());
        return cellStyle1;
    }

}
