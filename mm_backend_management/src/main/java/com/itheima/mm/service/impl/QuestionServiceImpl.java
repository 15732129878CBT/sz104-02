package com.itheima.mm.service.impl;

import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.QuestionItemDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.QuestionItem;
import com.itheima.mm.pojo.Tag;
import com.itheima.mm.service.QuestionService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cbt
 * @createDate 2021-10-31
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionItemDao questionItemDao;

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) throws IOException {
        //1. 查询总条数
        Long total = questionDao.findTotal(queryPageBean);

        //2. 查询当前页数据集合
        List<Question> questionList = questionDao.findPageList(queryPageBean);

        return new PageResult(total,questionList);
    }

    @Override
    @Transactional
    public void add(Question question) {
        //添加事务
            //1.将题目自身的信息存储到 t_question表
            questionDao.add(question);
            //2.将题目的选项信息存储到 t_question_item表
            //获取题目选项列表
            List<QuestionItem> questionItemList = question.getQuestionItemList();
            if (questionItemList != null) {
                for (QuestionItem questionItem : questionItemList) {
                    //遍历出每一个题目选项 添加选项信息到数据库
                    //添加之前设置每一个选项的所属试题id
                    questionItem.setQuestionId(question.getId());
                    questionItemDao.add(questionItem);
                }
            }
            //3.关联题目和标签（tag）
            List<Tag> tagList = question.getTagList();
            if (tagList != null) {
                for (Tag tag : tagList) {
                    Map paramMap = new HashMap();
                    paramMap.put("questionId", question.getId());
                    paramMap.put("tagId", tag.getId());
                    questionDao.associationQuestionAndTag(paramMap);
                }
            }


    }
}
