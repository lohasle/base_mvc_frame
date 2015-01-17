package com.lohasle.baseframe.s4m3.util.poi.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lohasle.baseframe.s4m3.util.file.FileUtils;
import com.lohasle.baseframe.s4m3.util.poi.model.HSSFExcelData;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

/**
 * excel 导出工具类
 *
 * @author fule
 * @version Revision 1.0.0
 * @see:
 * @创建日期：2013-10-22
 * @功能说明：
 */
public class ExportExcelUtil {

    private static final Logger log = Logger.getLogger(ExportExcelUtil.class);

    /**
     * 公式标示
     */
    public static final String FORMULA = "formula->";

    private ExportExcelUtil() {
    }

    /**
     * @param wb
     * @param sheetName
     * @return
     * @功能说明:创建HSSFSheet工作簿 .
     * @author fule
     * 2013-10-23 fule
     */
    public static HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
        HSSFSheet sheet = wb.createSheet(sheetName);
        //sheet.setDefaultColumnWidth(12);// 默认列数
        sheet.setGridsPrinted(true);// 显示单元格边界线
        sheet.setDisplayGridlines(true);
        return sheet;
    }

    /**
     * @param wb
     * @return
     * @throws java.io.IOException
     * @功能说明:得到excel 工作文件 字节
     * .
     * @author fule
     * 2013-10-22 fule
     */
    public static byte[] getWorkbookBytes(HSSFWorkbook wb) throws IOException {
        if (wb == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);
        out.flush();
        return out.toByteArray();
    }

    /**
     * @param wb
     * @param fileName
     * @功能说明:将excel写入对应文件 .
     * @author fule
     * 2013-10-22 fule
     */
    public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()));
        } catch (IOException e) {
            log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()));
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                log.error(new StringBuffer("[").append(e.getMessage()).append("]")
                        .append(e.getCause()));
            }
        }
    }

    public static final HSSFWorkbook createHSSFWorkbook() {
        return new HSSFWorkbook();
    }


    /**
     * @param excelDatas excel 工作表 数据对象 集合
     * @param headHigh   表头是否高亮
     * @return
     * @throws java.io.IOException
     * @功能说明:根据sheetDateMapList数据创建excel输出到字节中 默认的生成方法，带有了默认的样式
     * @author fule
     * 2013-10-23 fule
     */
    public static byte[] generateDefaultExcel(HSSFWorkbook wb, List<HSSFExcelData> excelDatas, boolean headHigh) throws IOException {
        if (excelDatas != null && excelDatas.size() > 0) {
            HSSFExcelData indexExcelData = excelDatas.get(0);
            HSSFCellStyle bodyCellStyle = indexExcelData.getBodyDefaultStyle(wb);
            HSSFCellStyle headCellStyle = headHigh ? indexExcelData.getHeadDefaultStyle(wb) : bodyCellStyle;
            for (HSSFExcelData excelData : excelDatas) {
                if (excelData.getBodyCellStyle() == null) {
                    //无自定义的内容样式 采用默认的
                    excelData.setBodyCellStyle(bodyCellStyle);
                }
                if (excelData.getHeadCellStyle() == null) {
                    //无自定义的表头样式 采用默认的
                    excelData.setHeadCellStyle(headCellStyle);
                }
                excelData.setEnableFreezeHead(true);//默认启用 冻结表头
                HSSFSheet sheet = createSheet(wb, excelData.getSheetName());
                excelData.writeSheetData(sheet, excelData);
            }
            return getWorkbookBytes(wb);
        }
        return null;
    }

    /**
     * @param excelDatas excel 工作表 数据对象 集合
     * @param filePath
     * @throws java.io.IOException
     * @功能说明:根据sheetDateMapList数据创建excel输出到文件中 默认的生成方法，带有了默认的样式
     * @author fule
     * 2013-10-23 fule
     */
    public static void generateDefaultExcelToFile(HSSFWorkbook wb, List<HSSFExcelData> excelDatas, String filePath)
            throws IOException {
        byte[] content = generateDefaultExcel(wb, excelDatas, true);
        FileUtils.saveByteToFile(content, filePath);
    }


    /**
     * @param sheetDateMapList excel 工作表 数据对象 集合
     * @return
     * @throws java.io.IOException
     * @功能说明:根据sheetDateMapList数据创建excel输出到字节中
     * @author fule
     * 2014-03-21 fule
     */
    public static byte[] generateExcel(HSSFWorkbook wb, List<HSSFExcelData> sheetDateMapList) throws IOException {
        for (HSSFExcelData simpleExcelData : sheetDateMapList) {
            HSSFSheet sheet = createSheet(wb, simpleExcelData.getSheetName());
            simpleExcelData.writeSheetData(sheet, simpleExcelData);
        }
        return getWorkbookBytes(wb);
    }

    /**
     * @param excelDatas excel 工作表 数据对象 集合
     * @param filePath
     * @throws java.io.IOException
     * @功能说明:根据sheetDateMapList数据创建excel输出到文件中 .
     * @author fule
     * 2013-10-23 fule
     */
    public static void generateExcelToFile(HSSFWorkbook wb, List<HSSFExcelData> excelDatas, String filePath)
            throws IOException {
        byte[] content = generateExcel(wb, excelDatas);
        FileUtils.saveByteToFile(content, filePath);
    }


}
