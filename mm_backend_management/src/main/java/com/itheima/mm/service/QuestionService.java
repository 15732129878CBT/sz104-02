package com.itheima.mm.service;

import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.io.IOException;

/**
 * @author Cbt
 * @createDate 2022-03-01 15:59
 */
public interface QuestionService {
    PageResult findByPage(QueryPageBean queryPageBean) throws IOException;

    void add(Question question);
}
