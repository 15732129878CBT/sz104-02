package com.itheima.mm.service;

import com.itheima.mm.pojo.User;

import java.io.IOException;

/**
 * @author Cbt
 * @createDate 2022-03-01 15:59
 */
public interface UserService {
    //1.校验用户名
    User findUser(User parameterUser) throws IOException;
}
