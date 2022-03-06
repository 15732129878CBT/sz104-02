package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * @author cbt
 * @createDate 2021-10-25
 */
public interface QuestionDao {
    /**
     * 查询学科对应的题目数量
     * @param courseId
     * @return
     */
    Long findQuestionCountByCourseId(Integer courseId);

    /**
     * 查询对应条件的题目数量
     * @param queryPageBean
     * @return
     */
    Long findTotal(QueryPageBean queryPageBean);

    /**
     * 根据相应条件的题目数量，返回结果集
     * @param queryPageBean
     * @return
     */
    List<Question> findPageList(QueryPageBean queryPageBean);

    /**
     * 新增试题信息
     * @param question
     */
    void add(Question question);

    /**
     * 关联题目和tag（标签）
     * @param paramMap
     */
    void associationQuestionAndTag(Map paramMap);
}
