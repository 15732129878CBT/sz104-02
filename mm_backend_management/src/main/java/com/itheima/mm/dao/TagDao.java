package com.itheima.mm.dao;

import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * @author cbt
 * @createDate 2021-10-25
 */
public interface TagDao {

    /**
     * 查询学科对应标签的数量
     * @param courseId
     * @return
     */
    Long findTagCountByCourseId(Integer courseId);

    /**
     * 查询学科对应标签的数量
     * @param courseId
     * @return
     */
    List<Tag> findTagListByCourseId(Integer courseId);
}
