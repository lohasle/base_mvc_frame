package com.lohasle.baseframe.s4m3.common;

/**
 * Created with IntelliJ IDEA.
 * User: fule
 * Date: 14-05-28
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */


import java.util.List;

/**
 * 基础 DAO
 */
public interface BaseDao<T extends AbstractDO, PK extends java.io.Serializable>{

    //查询
    List<T> findAll();

    //查询带条件
    List<T> findOnFilter(T t);


    //查找
    T load(PK modelPk);

    //删除
    void delete(PK modelPk);

    //修改
    void update(T t);

    //添加返回主键
    PK insert(T t);

    //记录数
    int countAll();
}
