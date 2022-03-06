package com.itheima.mm.controller;

import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * @author cbt
 * @createDate 2021-10-31
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/page")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) throws IOException {
        try {
            //1. 获取请求参数，封装到QueryPageBean对象中
            //2. 调用业务层的方法，获取分页数据
            PageResult pageResult = questionService.findByPage(queryPageBean);
            //查询成功
            return new Result(true,"分页查询基础题目列表成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"分页查询基础题目列表失败");
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Question question, HttpSession session) throws IOException {
        try {
            //1.获取请求参数
            /**
             * 手动设置需要的数据 status ReviewStatus createDate
             * 题目状态
                 0 待发布（待审核、已拒绝）
                 1 已发布（已审核）
                 2 已下架（已审核）
               审核状态(ReviewStatus)：  0 待审核、1 已审核、2 已拒绝
             */
            question.setStatus(0);
            question.setReviewStatus(0);
            question.setCreateDate(DateUtils.parseDate2String(new Date()));
            User user = (User)session.getAttribute(Constants.USER_SESSION_KEY);
            question.setUserId(user.getId());
            //2.调用业务层方法存储试题信息
            questionService.add(question);
            return new Result(true,"添加试题成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加试题失败");
        }
    }
}
