package com.itheima.mm.service.impl;

import com.itheima.mm.dao.UserDao;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author cbt
 * @createDate 2021-10-07
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    //1.校验用户名
    @Override
    public User findUser(User parameterUser) throws IOException {
        User loginUser = userDao.findUserByUsername(parameterUser.getUsername());

        //1.1判断用户是否为空 判断用户输入是否正确
        if (loginUser != null) {
            //1.2判断密码是否正确
            if (parameterUser.getPassword().equals(loginUser.getPassword())) {
                return loginUser;
            } else{
                throw new RuntimeException("密码错误!");
            }
        } else {
            throw new RuntimeException("用户名错误");
        }
    }

    //2.校验密码
}
