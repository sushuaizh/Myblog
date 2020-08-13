package com.ssz.blog.service;

import com.ssz.blog.po.User;

/**
 * @author sushuaizhen
 * @date 2020/7/25
 */
public interface UserService {

    User checkUser(String username,String password);
}
