package com.itheima.mm.service.impl;

import com.itheima.mm.dao.CatalogDao;
import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.TagDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.service.CourseService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author cbt
 * @createDate 2021-10-16
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CatalogDao catalogDao;


    @Override
    public void addCourse(Course course) throws IOException {
        courseDao.addCourse(course);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) throws IOException {
        Map map = queryPageBean.getQueryParams();
        if (map!=null) {
            if (!map.get("status").equals("")) {
                String status = (Integer)map.get("status")+"";
                map.put("status", status);
            }
        }

        //1.查询总条数
        Long totalCourse = courseDao.findTotalCourse(queryPageBean);
        System.out.println(totalCourse);
        //2.查询当前页结果
        List<Course> list = courseDao.findCurrentPage(queryPageBean);

        return new PageResult(totalCourse,list);
    }

    @Override
    public void updateCourse(Course course) throws IOException {
        courseDao.updateCourse(course);
    }

    @Override
    public void deleteCourse(Integer courseId) throws Exception {
        SqlSession sqlSession = null;
        try {
            //删除之前要先进行判断
            //1. 判断当前学科是否有关联的二级目录: 以当前学科的id到t_catalog表中查询二级目录的数量，如果数量为0表示没有关联的二级目录
            Long catalogCount = catalogDao.findCatalogCountByCourseId(courseId);
            if (catalogCount != 0) {
                //有关联的二级目录，不能删除
                throw new RuntimeException("有关联的二级目录，不能删除");
            }

            //2. 判断当前学科是否有关联的标签
            TagDao tagDao = sqlSession.getMapper(TagDao.class);
            Long tagCount = tagDao.findTagCountByCourseId(courseId);
            if (tagCount != 0) {
                //有关联的标签，不能删除
                throw new RuntimeException("有关联的标签，不能删除");
            }

            //3. 判断当前学科是否有关联的题目
            QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
            Long questionCount = questionDao.findQuestionCountByCourseId(courseId);
            if (questionCount != 0) {
                //有关联的题目，不能删除
                throw new RuntimeException("有关联的题目，不能删除");
            }

            //可以删除，调用CourseDao的方法进行删除
            CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
            courseDao.deleteCourseByID(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Course> findAll(Map parameterMap) throws IOException {
        if (parameterMap.get("status")!=null) {
            String status = (Integer)parameterMap.get("status") + "";
            parameterMap.put("status", status);
        }
        //调用dao层的方法，查询所有学科信息
        List<Course> courseList = courseDao.findAll(parameterMap);
        return courseList;
    }
}
