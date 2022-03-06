package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;

import java.util.List;

/**
 * @author cbt
 * @createDate 2021-10-25
 */
public interface CatalogDao {

    /**
     * 查询学科对应的二级目录的数量
     * @param courseId
     * @return
     */
    Long findCatalogCountByCourseId(Integer courseId);

    /**
     * 查询学科对应的二级目录列表
     * @param courseId
     * @return
     */
    List<Catalog> findCatalogListByCourseId(Integer courseId);
}
