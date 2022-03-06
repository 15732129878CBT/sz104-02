package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.util.List;
import java.util.Map;

/**
 * @author cbt
 * @createDate 2021-10-16
 */
public interface CourseDao {
    /**
     * 添加学科
     * @param course
     */
    void addCourse(Course course);

    /**
     * 查询总条数
     * @param queryPageBean
     * @return
     */
    Long findTotalCourse(QueryPageBean queryPageBean);

    /**
     * 查询当前页结果
     * @param queryPageBean
     * @return
     */
    List<Course> findCurrentPage(QueryPageBean queryPageBean);

    /**
     * 修改学科信息
     * @param course
     */
    void updateCourse(Course course);

    /**
     * 删除学科信息
     * @param course
     */
    void deleteCourseByID(Integer course);

    /**
     * 查询全部学科名称
     * @param parameterMap
     * @return
     */
    List<Course> findAll(Map parameterMap);
}
