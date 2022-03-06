package com.itheima.mm.controller;

import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cbt
 * @createDate 2021-10-14
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/add")
    public Result add(@RequestBody Course course, HttpSession session) throws IOException {
        try {
            //2.设置course的其它参数createDate,userid,,orderid
            course.setCreateDate(DateUtils.parseDate2String(new Date()));
            course.setOrderNo(2);
            //从session中获取当前用户
            User loginUser = (User) session.getAttribute(Constants.USER_SESSION_KEY);
            course.setUserId(loginUser.getId());
            //3.调用业务层方法 保存学科信息
            courseService.addCourse(course);

            //4. 无异常 添加学科成功 把返回结果封装到result
            return  new Result(true,"添加学科成功");
        } catch (IOException e) {
            e.printStackTrace();
            //4.1 有异常 添加学科失败
            return  new Result(false,"添加学科失败");
        }
    }

    @RequestMapping("/list")
    public Result getList(@RequestBody QueryPageBean queryPageBean) throws IOException {
        try {
            //2.调用业务层的方法查询数据
            PageResult pageResult =  courseService.findByPage(queryPageBean);
            System.out.println(pageResult);
            //3.
            return new Result(true, "查询分布列表成功", pageResult);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "查询分布列表失败");
        }
    }

    @RequestMapping("/update")
    public Result updateCourse(@RequestBody Course course) throws IOException {
        try {
            //1.获取请求参数
            //2.调用业务层的方法修改学科名称 及是否显示
            courseService.updateCourse(course);
            //3.查询分布列表成功
            return new Result(true, "修改学科信息成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "修改学科信息失败");
        }
    }

    @RequestMapping("/delete")
    public Result deleteCourse(int courseId) throws Exception {
        try {
            //1.获取请求参数
            //2.调用业务层的方法修改学科名称 及是否显示
            courseService.deleteCourse(courseId);
            //3.查询分布列表成功
            return new Result(true,"删除学科信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(@RequestBody Map parameterMap) throws IOException {
        try {
            //获取客户端携带的请求参数
            //调用业务层的方法，加载所有学科信息
            List<Course> courseList = courseService.findAll(parameterMap);
            //没有异常，则查询成功
            return new Result(true,"加载所有学科成功",courseList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"加载所有学科失败");
        }
    }
}
