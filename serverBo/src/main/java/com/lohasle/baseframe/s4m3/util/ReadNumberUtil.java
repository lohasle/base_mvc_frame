package com.lohasle.baseframe.s4m3.util;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * Created with IntelliJ IDEA.
 * User: fule
 * Date: 13-7-17
 * Time: 下午10:14
 * To change this template use File | Settings | File Templates.
 * 数字 大小写转化 金额转化工具类
 */
public class ReadNumberUtil {
    /**
     * 大写数字
     */
    private static final String[]             NUMBERS     = {"零", "壹", "贰", "叁", "肆", "伍", "陆",
            "柒", "捌", "玖"                                };
    
    /**
     * 数字大写 键值对
     */
    private final static Map<Integer, String> CAPITAL_MAP = new HashMap<Integer, String>();
    
    /**
     * 整数部分的单位
     */
    private static final String[]             IUNIT       = {"", "拾", "佰", "仟", "万", "十", "百", "仟",
            "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"       };
    
    /**
     * 用于金额的圆角分
     */
    private static final String[]             YJZ         = {"圆", "角", "分" };
    
    /**
     * 初始化键值对
     */
    static {
        for (int i = 0; i < 9; i++) {
            CAPITAL_MAP.put(i + 1, NUMBERS[i]);
        }
    }
    
    /**
     * 得到大写
     */
    public static String toChinese(String str) {
        String integerStr;// 整数部分数字
        String decimalStr;// 小数部分数字
        
        if (str.indexOf(".") > 0) {
            integerStr = str.substring(0, str.indexOf("."));
            decimalStr = str.substring(str.indexOf(".") + 1);
        } else if (str.indexOf(".") == 0 || str.indexOf(".") == 1) {
            integerStr = "";
            decimalStr = str.substring(1);
        } else {
            integerStr = str;
            decimalStr = "";
        }
        if (!integerStr.equals("")) {
            integerStr = Long.toString(Long.parseLong(integerStr));
            if (integerStr.equals("0")) {
                integerStr = "";
            }
        }
        // overflow超出处理能力，直接返回
        if (integerStr.length() > IUNIT.length) {
            System.out.println(str + ":超出处理能力");
            return str;
        }
        int[] integers = toArray(integerStr);// 整数部分数字
        boolean isMust5 = isMust5(integerStr);// 设置万单位
        String result = "".equals(decimalStr) ? getChineseInteger(integers, isMust5)
            : getChineseInteger(integers, isMust5) + "." + decimalStr;
        return result.indexOf(".") == 0 ? "0" + result
            : result;
    }
    
    /**
     * 金额转化 取到角分
     * 暂时支持人民币
     * 
     * @param str
     * @return
     */
    public static String moneyNumtoChinese(String str, String moneyType) {
        if ("RMB".equalsIgnoreCase(moneyType)) {
            if (str == null || "".equals(str) || 0 == Integer.parseInt(str)) {
                return "";
            }
            String pixStr = "人民币:";
            String te_str = toChinese(str);
            String[] strs = te_str.split("\\.");
            if (strs.length == 1) {
                return pixStr + strs[0] + YJZ[0] + "整";
            } else {
                char[] ch = strs[1].toCharArray();
                int index = 0;
                String angle = "";// 角
                String minute = "";// 分
                for (char c : ch) {
                    if (index == 0) {
                        angle = CAPITAL_MAP.get((int) c - 48) + "角";
                    } else if (index == 1) {
                        minute = CAPITAL_MAP.get((int) c - 48) + "分";
                    }
                    index++;
                }
                return pixStr + strs[0] + YJZ[0] + angle + minute;
            }
        } else {
            return moneyNumtoChinese(str, "RMB");
        }
    }
    
    /**
     * 整数部分和小数部分转换为数组，从高位至低位
     */
    private static int[] toArray(String number) {
        int[] array = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            array[i] = Integer.parseInt(number.substring(i, i + 1));
        }
        return array;
    }
    
    private static String getChineseInteger(int[] integers, boolean isMust5) {
        StringBuffer chineseInteger = new StringBuffer("");
        int length = integers.length;
        for (int i = 0; i < length; i++) {
            String key = "";
            if (integers[i] == 0) {
                if ((length - i) == 13)// 万(亿)(必填)
                    key = IUNIT[4];
                else if ((length - i) == 9)// 亿(必填)
                    key = IUNIT[8];
                else if ((length - i) == 5 && isMust5)// 万(不必填)
                    key = IUNIT[4];
                // 0遇非0时补零，不包含最后一位
                if ((length - i) > 1 && integers[i + 1] != 0)
                    key += NUMBERS[0];
            }
            chineseInteger.append(integers[i] == 0 ? key
                : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
        }
        return chineseInteger.toString();
    }
    
    /**
     * 将数字转化成国际上三位插入一个逗号的表示方法
     * 如 499,863
     * 
     * @return
     * @throws java.util.zip.DataFormatException
     */
    public static String formatToCommma(String num) throws DataFormatException {
        if (num.indexOf(".") != -1) {
            throw new DataFormatException();
        }
        String str = num;
        int dcount = str.length() / 3;// 需要插入几个逗号
        int len = str.length() % 3 == 0 ? dcount
            : dcount + 1;// 存放三元组的个数
        String[] strs = new String[len];
        for (int i = 0; i < (len - 1); i++) {
            strs[i] = str.substring(str.length() - 3, str.length());
            str = str.substring(0, str.length() - 3);
        }
        strs[len - 1] = str;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(strs[len - i - 1]);
            if (i != len - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    /**
     * 判断第5位数字的单位"万"是否应加。
     */
    private static boolean isMust5(String integerStr) {
        int length = integerStr.length();
        if (length > 4) {
            String subInteger = "";
            if (length > 8) {
                // 取得从低位数，第5到第8位的字串
                subInteger = integerStr.substring(length - 8, length - 4);
            } else {
                subInteger = integerStr.substring(0, length - 4);
            }
            return Integer.parseInt(subInteger) > 0;
        } else {
            return false;
        }
    }
    
}
