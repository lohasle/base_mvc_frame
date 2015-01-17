package com.lohasle.baseframe.s4m3.util.poi.tools;
import com.lohasle.baseframe.s4m3.pojo.JxlsMutilSheetData;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.Map;

/**
 * Created by fule on 14-5-21.
 * <p/>
 * 使用excel模板操作excel ,用于生成excel报表 ，图表等
 * <p/>
 * 说明：beanParams 数据对象 为一个map，map可以存放对象，列表，map等。对应着为excel 模板中的表达式写法
 * <p/>
 * 参见：http://jxls.sourceforge.net/reference/tags.html
 */
public class JxlsUtils {


    /**
     * 根据模板excel 生成excel到文件
     *
     * @param tmplFilePath 模板文件路径
     * @param beanParams   数据对象
     * @param destFilePath 生成excel 地址
     * @return
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public final static File exportExcelToFile(String tmplFilePath, Map beanParams,
                                               String destFilePath) throws IOException, InvalidFormatException {
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(tmplFilePath, beanParams, destFilePath);
        return new File(destFilePath);
    }


    /**
     * 根据模板excel 生成excel到输出流
     *
     * @param tmplFilePath 模板文件路径
     * @param beanParams   数据对象
     * @return
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public final static byte[] exportExcelToBytes(String tmplFilePath, Map beanParams) throws IOException, InvalidFormatException {
        XLSTransformer transformer = new XLSTransformer();
        Workbook wb = transformer.transformXLS(new FileInputStream(tmplFilePath), beanParams);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);
        out.flush();
        return out.toByteArray();
    }


    /**
     * 根据模板excel 生成excel到文件
     *
     * @param tmplFilePath   模板文件路径
     * @param mutilSheetData excel  多sheet 工作表对象
     * @param destFilePath   生成excel 地址
     * @return
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public final static File exportMutilSheetExcelToFile(String tmplFilePath, JxlsMutilSheetData mutilSheetData, String destFilePath)
            throws IOException, InvalidFormatException {
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = createMutilSheetWorkboook(tmplFilePath, mutilSheetData, transformer);
        workbook.write(new FileOutputStream(destFilePath));
        return new File(destFilePath);
    }

    private static Workbook createMutilSheetWorkboook(String tmplFilePath, JxlsMutilSheetData mutilSheetData, XLSTransformer transformer) throws InvalidFormatException, FileNotFoundException {
        return transformer.transformMultipleSheetsList(new FileInputStream(tmplFilePath),
                mutilSheetData.getObjects(), mutilSheetData.getNewSheetNames(), mutilSheetData.getBeanName(),
                mutilSheetData.getBeanParams(), mutilSheetData.getStartSheetNum());
    }


    /**
     * 根据模板excel 生成excel到文件
     *
     * @param tmplFilePath   模板文件路径
     * @param mutilSheetData excel  多sheet 工作表对象
     * @return
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public final static byte[] exportMutilSheetExcelToBytes(String tmplFilePath, JxlsMutilSheetData mutilSheetData) throws IOException, InvalidFormatException {
        XLSTransformer transformer = new XLSTransformer();
        Workbook wb = createMutilSheetWorkboook(tmplFilePath, mutilSheetData, transformer);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);
        out.flush();
        return out.toByteArray();
    }


}
