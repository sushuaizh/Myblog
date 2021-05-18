package com.ssz.blog.service;

import com.ssz.blog.pojo.User;

/**
 * @author sushuaizhen
 * @date 2020/7/25
 */
public interface UserService {

    User checkUser(String username,String password);
}
