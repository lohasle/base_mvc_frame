package com.lohasle.baseframe.s4m3.pojo.interf;

/**
 * Created by fule on 14-6-12.
 */

import java.util.List;

/**
 * 兼容老系统的分页适配器
 */
public interface PageBeanAdapter {
    PageBeanAdapter instanceNew(int pageNum, int pageSize, int total, List rows);
}
