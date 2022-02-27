package com.ssz.blog.service;

import com.ssz.blog.pojo.User;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/7/25
 */
public interface UserService {

    User checkUser(String username,String password);

    User getUserByName(String name);

    User saveUser(User user);

    void delete(Long id);

    User updateUser(Long id, User user);

    User getUserById(Long id);

    List<User> getUsers();




}
