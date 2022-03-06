package com.itheima.mm.service;

import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Cbt
 * @createDate 2022-03-01 15:59
 */
public interface CourseService {
    void addCourse(Course course) throws IOException;

    PageResult findByPage(QueryPageBean queryPageBean) throws IOException;

    void updateCourse(Course course) throws IOException;

    void deleteCourse(Integer courseId) throws Exception;

    List<Course> findAll(Map parameterMap) throws IOException;
}
