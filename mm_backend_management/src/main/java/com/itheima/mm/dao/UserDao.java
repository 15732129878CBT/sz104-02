package com.itheima.mm.dao;

import com.itheima.mm.pojo.User;

/**
 * @author cbt
 * @createDate 2021-10-07
 */
public interface UserDao {
    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
