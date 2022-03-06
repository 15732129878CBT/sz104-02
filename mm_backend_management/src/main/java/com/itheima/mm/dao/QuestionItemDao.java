package com.itheima.mm.dao;

import com.itheima.mm.pojo.QuestionItem;

/**
 * @author cbt
 * @createDate 2021-11-13
 */
public interface QuestionItemDao {
    /**
     *  添加题目选项信息
     * @param questionItem
     */
    void add(QuestionItem questionItem);
}
