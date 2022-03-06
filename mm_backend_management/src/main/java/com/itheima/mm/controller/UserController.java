package com.itheima.mm.controller;

import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author cbt
 * @createDate 2021-10-07
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(@RequestBody User parameterUser, HttpSession session) throws IOException {
        try {
            //1.封装请求参数
            System.out.println("parameterUser:"+parameterUser.toString());
            //2.调用service层方法，校验用户名和密码
            User loginUser = userService.findUser(parameterUser);
            System.out.println("loginUser:"+loginUser.toString());
            /*
                保存登录状态！！！ 重要 登录成功之后要保存登录状态，要不然跳转到其它界面 其它界面并不知道你是已登录的状态
                保存登录状态有多种方法
                    1.使用session
                    2.使用token
                    3.使用cookie
             */
            session.setAttribute(Constants.USER_SESSION_KEY,loginUser);
            //3.把返回结果封装到result
            return new Result(true,"登录成功",loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpSession session) throws IOException {
        try {
            //清除登录状态
            session.invalidate();
            return new Result(true,"清除登录状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"清除登录状态失败");
        }
    }
	
	@RequestMapping("/log")
    public Result logout(HttpSession session) throws IOException {
        try {
            //清除登录状态
            session.invalidate();
            return new Result(true,"清除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"清除失败");
        }
    }
	
	@GetMapping("/list")
    public String save(Model model) {
        List<Items> list = itemsService.findAll();
        model.addAttribute("items", list);
        return "items";
    }
}
