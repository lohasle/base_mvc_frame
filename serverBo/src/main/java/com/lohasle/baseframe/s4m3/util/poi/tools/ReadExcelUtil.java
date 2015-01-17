package com.lohasle.baseframe.s4m3.util.poi.tools;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Excel 通用 读取 工具
 * 目前支持03 xls 格式
 *
 * @author 付乐
 * @createTime 2013-7-25
 */
public class ReadExcelUtil {
    private static final Logger logger = Logger.getLogger(ReadExcelUtil.class);

    /**
     * 读取 2003 excel
     *
     * @param beginDataHeard 数据表头开始位置
     * @return 工作表 的数据
     * List<Map<String,Object>> 每一行为一个Map 每一个map存储着对应的列名和值
     * @throws java.io.IOException
     */
    public static List<Map<String, Object>> loadXlsSheet(Sheet sheet, int beginDataHeard)
            throws IOException {
        if (sheet == null) {
            return null;
        }
        List<Map<String, Object>> result_list = new ArrayList<Map<String, Object>>();
        Iterator<Row> rows = sheet.rowIterator();
        HSSFRow heard_row = null;
        for (int i = 0; i <= beginDataHeard; i++) {
            heard_row = (HSSFRow) rows.next();
        }
        String[] heard_title_str = getHeardRowTitles(heard_row);
        while (rows.hasNext()) {
            Map<String, Object> row_map_unit = new HashMap<String, Object>();
            HSSFRow row = (HSSFRow) rows.next();
            logger.info("正在读取第" + row.getRowNum() + "行");
            int index_point_cell = 0;// 当前读取的列号
            for (int i = 0; i < heard_title_str.length; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    row_map_unit.put(heard_title_str[index_point_cell], null);
                    logger.info("第" + index_point_cell + "个单元格的值是："
                            + heard_title_str[index_point_cell] + "---->" + "null");
                    index_point_cell++;
                    continue;
                }
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        Object value = HSSFDateUtil.isCellDateFormatted(cell) ? HSSFDateUtil
                                .getJavaDate(cell.getNumericCellValue())
                                : new BigDecimal(cell.getNumericCellValue()).setScale(2,
                                BigDecimal.ROUND_HALF_UP).doubleValue();// 如果是小数
                        // 去两位并四舍五入
                        row_map_unit.put(heard_title_str[index_point_cell], value);
                        logger.info("第" + index_point_cell + "个单元格的值是："
                                + heard_title_str[index_point_cell] + "---->" + value);
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        row_map_unit.put(heard_title_str[index_point_cell], cell
                                .getStringCellValue().trim());
                        logger.info("第" + index_point_cell + "个单元格的值是："
                                + heard_title_str[index_point_cell] + "---->"
                                + cell.getStringCellValue().trim());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        row_map_unit.put(heard_title_str[index_point_cell],
                                cell.getBooleanCellValue());
                        logger.info("第" + index_point_cell + "个单元格的值是："
                                + heard_title_str[index_point_cell] + "---->"
                                + cell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        Double dcell = new BigDecimal(cell.getNumericCellValue()).setScale(2,
                                BigDecimal.ROUND_HALF_UP).doubleValue(); // 去两位并四舍五入
                        row_map_unit.put(heard_title_str[index_point_cell], dcell);
                        logger.info("第" + index_point_cell + "个单元格的值是："
                                + heard_title_str[index_point_cell] + "---->" + dcell);
                        break;
                    default:
                        row_map_unit.put(heard_title_str[index_point_cell], null);
                        logger.debug("row:" + index_point_cell + "\tunsuported sell type");
                        logger.info("第" + index_point_cell + "个单元格的值是："
                                + heard_title_str[index_point_cell] + "---->" + "null");
                        break;
                }
                index_point_cell++;
            }
            boolean newMapFlag = false;
            for (Entry<String, Object> entry : row_map_unit.entrySet()) {
                if (entry.getValue() != null && !"".equals(entry.getValue())) {
                    //有一列有值
                    newMapFlag = true;
                }
            }
            if (newMapFlag) {
                result_list.add(row_map_unit);
            }
        }
        return result_list;
    }

    /**
     * 读取 2003 excel
     *
     * @param filePath       xls文件路径
     * @param sheetName      工作表名称
     * @param beginDataHeard 数据表头开始位置
     * @return 工作表 的数据
     * List<Map<String,Object>> 每一行为一个Map 每一个map存储着对应的列名和值
     * @throws java.io.IOException
     */
    public static List<Map<String, Object>> loadXls(String filePath, String sheetName,
                                                    int beginDataHeard) throws IOException {
        InputStream input = null;
        try {
            input = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            logger.debug("读取文件" + filePath + " 出错");
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        return loadXls(input, sheetName, beginDataHeard);
    }

    /**
     * 读取 2003 excel
     *
     * @param sheetName      工作表名称
     * @param beginDataHeard 数据表头开始位置
     * @return 工作表 的数据
     * List<Map<String,Object>> 每一行为一个Map 每一个map存储着对应的列名和值
     * @throws java.io.IOException
     */
    public static List<Map<String, Object>> loadXls(InputStream input, String sheetName,
                                                    int beginDataHeard) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(input);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheet(sheetName); // 工作表sheetName
        return loadXlsSheet(sheet, beginDataHeard);
    }

    /**
     * 读取单元格的值
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        return HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    } else {
                        return cell.getNumericCellValue();
                    }
                case HSSFCell.CELL_TYPE_STRING:
                    return cell.getStringCellValue().trim();
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
                case HSSFCell.CELL_TYPE_FORMULA:
                    return cell.getCellFormula();
                default:
                    logger.debug("cell:" + cell + "\tunsuported sell type");
                    return null;
            }
        } else {
            return null;
        }

    }

    /**
     * 取得单元格 字符值
     *
     * @param cell
     * @return
     */
    public static String getStringCellValue(Cell cell) {
        return getCellValue(cell) instanceof String ? (String) getCellValue(cell)
                : String.valueOf(getCellValue(cell));
    }

    /**
     * 取得单元格 字符值
     *
     * @param cell
     * @return
     */
    public static Date getDateCellValue(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (obj == null || "".equals(obj)) {
                return null;
            }
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-mm-dd");
            try {
                return (Date) sm.parseObject(str);
            } catch (ParseException e) {
                logger.debug(str + "转化成日期失败");
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取表头标题
     *
     * @param row
     * @return
     */
    private static String[] getHeardRowTitles(HSSFRow row) {
        List<String> result_list = new ArrayList<String>();
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            HSSFCell cell = (HSSFCell) cells.next();
            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                result_list.add(cell.getStringCellValue());
            }
        }
        return result_list.toArray(new String[]{});
    }

}
